package com.virtlink.editorservices.vfs

import sun.plugin.dom.exception.InvalidStateException
import java.util.*

/**
 * A path to a virtual file.
 *
 * @property path The path.
 */
class Path private constructor(private val path: String, private val _components: Lazy<List<PathComponent>>) {

    constructor(path: String) : this(path, lazy { normalizePath(parsePath(path)) })

    constructor(vararg components: PathComponent) : this(components.asIterable())

    constructor(components: Iterable<PathComponent>) : this(printPath(components), lazy { normalizePath(components.toList()) })

    /** The component list. */
    val components: List<PathComponent> get() = this._components.value

    /**
     * Gets whether this path is absolute.
     *
     * An absolute path starts with a path separator.
     */
    val isAbsolute get() = this.path.isNotEmpty() && this.path[0] == pathSeparator

    /**
     * Gets whether this path points to a file.
     *
     * A file path does not end with a path separator.
     */
    val isFile get() = this.components.lastOrNull()?.kind == PathComponentKind.File

    /**
     * Gets the path to the parent of this path; or null when this path has no parent.
     */
    val parent: Path? get() = if (this.components.isNotEmpty()) Path(this.components.take(this.components.size - 1)) else null

    /**
     * Appends the specified path to this path.
     *
     * If the other path is absolute, only the other path is returned.
     *
     * @param other The other path.
     * @return The resulting path.
     */
    fun append(other: Path): Path {
        if (other.isAbsolute) return other

        return if (this.isFile) {
            Path(this.components.take(this.components.size - 1) + other.components)
        } else {
            Path(this.components + other.components)
        }
    }

    /**
     * Returns this path as referencing a file.
     *
     * If the path already referenced a file, the path itself is returned.
     *
     * @return The file path.
     */
    fun asFile(): Path {
        if (this.isFile)
            return this

        val newComponents = this.components.toTypedArray()
        val lastComponent = newComponents.last()

        if (lastComponent.isSpecial)
            throw InvalidStateException("This path cannot be converted to a file.")

        newComponents[newComponents.size - 1] = PathComponent.file(lastComponent.name!!)

        val newPath = Path(newComponents.asIterable())
        assert(newPath.isFile)
        return newPath
    }

    /**
     * Returns this path as referencing a folder.
     *
     * If the path already referenced a folder, the path itself is returned.
     *
     * @return The file path.
     */
    fun asFolder(): Path {
        if (!this.isFile)
            return this

        val newComponents = this.components.toTypedArray()
        val lastComponent = newComponents.last()

        if (lastComponent.isSpecial)
            return this

        newComponents[newComponents.size - 1] = PathComponent.folder(lastComponent.name!!)

        val newPath = Path(newComponents.asIterable())
        assert(!newPath.isFile)
        return newPath
    }

    override fun equals(other: Any?): Boolean {
        return equals(other as? Path)
    }

    fun equals(other: Path?): Boolean {
        return other != null
            && this.path == other.path
    }

    override fun hashCode(): Int {
        return this.path.hashCode()
    }

    override fun toString(): String {
        return this.path
    }


    companion object {

        /**
         * Gets the root path.
         */
        @JvmStatic @get:JvmName("rootPath") val rootPath = Path(PathComponent.root)

        /**
         * Gets the default path separator.
         */
        @JvmStatic @get:JvmName("pathSeparator") val pathSeparator =  '/'

        /**
         * Splits a path up into its constituent components.
         *
         * @param path The path.
         * @return The sequence of path components.
         */
        private fun parsePath(path: String): List<PathComponent> {
            if (path.isEmpty()) return emptyList()

            val separators = (0 until path.length).filter { path[it] == pathSeparator }

            val partCount = separators.size + if (separators.lastOrNull() == path.length - 1) 0 else 1

            val components = mutableListOf<PathComponent>()

            for (i in 0 until partCount)
            {
                val start = if (i == 0) 0 else separators[i - 1] + 1
                val end = if (i == separators.size) path.length else separators[i]

                val encodedName = path.substring(start, end - start)

                val isFolder = i < separators.size

                val kind = getKind(encodedName, isFolder)
                val component = toPathComponent(encodedName, kind)

                components[i] = component
            }
            return components
        }

        /**
         * Joins path components into a single path string.
         *
         * @param components The sequence of path components.
         * @return The path.
         */
        private fun printPath(components: Iterable<PathComponent>): String {
            val sb = StringBuilder()

            for (component in components) {
                val (encodedName, kind) = fromPathComponent(component)

                val isFolder = (kind != PathComponentKind.File)

                sb.append(encodedName)
                if (isFolder)
                    sb.append(pathSeparator)
            }

            return sb.toString()
        }

        /**
         * Normalizes the specified list of components.
         *
         * @param components The path components.
         * @return The normalized path components.
         */
        private fun normalizePath(components: Iterable<PathComponent>): List<PathComponent>
        {
            val normalized = Stack<PathComponent>()
            for (component in components) {
                when (component.kind)
                {
                    PathComponentKind.Root,
                    PathComponentKind.Folder,
                    PathComponentKind.File -> normalized.push(component)
                    PathComponentKind.Parent ->
                        if (normalized.isNotEmpty() && !normalized.peek().isSpecial)
                            normalized.pop()
                        else
                            normalized.push(component)
                    PathComponentKind.Current -> if (normalized.isEmpty()) normalized.push(component)
                }
            }
            return normalized.reversed()
        }

        /**
         * Gets the path component for the specified encoded name and kind.
         *
         * @param encodedName The encoded name.
         * @param kind The path component kind.
         * @return The path component.
         */
        private fun toPathComponent(encodedName: String, kind: PathComponentKind): PathComponent {
            return when (kind) {
                PathComponentKind.Root -> PathComponent.root
                PathComponentKind.Current -> PathComponent.current
                PathComponentKind.Parent -> PathComponent.parent
                PathComponentKind.Folder -> PathComponent.folder(decode(encodedName))
                PathComponentKind.File -> PathComponent.file(decode(encodedName))
            }
        }

        /**
         * Gets the encoded name and kind for the specified path component.
         *
         * @param component The path component.
         * @return A tuple with the encoded name and component kind.
         */
        private fun fromPathComponent(component: PathComponent): Pair<String, PathComponentKind> {
            val kind = component.kind
            val encodedName = when (kind) {
                PathComponentKind.Current -> "."
                PathComponentKind.Parent -> ".."
                PathComponentKind.Root -> ""
                else -> {
                    encode(component.name!!)
                }
            }
            return Pair(encodedName, kind)
        }

        /**
         * Gets the kind of path component.
         *
         * @param encodedComponent The encoded name.
         * @param isFolder Whether the component is a folder or file.
         * @return A member of the [PathComponentKind] enumeration.
         */
        private fun getKind(encodedComponent: String, isFolder: Boolean): PathComponentKind {
            return when {
                encodedComponent == "" -> PathComponentKind.Root
                encodedComponent == "." -> PathComponentKind.Current
                encodedComponent == ".." -> PathComponentKind.Parent
                isFolder -> PathComponentKind.Folder
                else -> PathComponentKind.File
            }
        }

        /**
         * Encodes the specified name.
         *
         * @param name The name to encode.
         * @return The encoded name.
         */
        private fun encode(decodedName: String): String {
            // TODO
            return decodedName
        }

        /**
         * Decodes the specified name.
         *
         * @param name The name to decode.
         * @return The decoded name.
         */
        private fun decode(encodedName: String): String {
            // TODO
            return encodedName
        }

    }
}