package com.virtlink.editorservices.vfs


/**
 * A path component.
 */
data class PathComponent private constructor(val kind: PathComponentKind, val name: String?) {

    companion object {

        /**
         * Creates a folder path component.
         */
        @JvmStatic fun folder(name: String) = PathComponent(PathComponentKind.Folder, name)

        /**
         * Creates a file path component.
         */
        @JvmStatic fun file(name: String) = PathComponent(PathComponentKind.File, name)

        /**
         * A root path component.
         */
        @JvmStatic @get:JvmName("root") val root = PathComponent(PathComponentKind.Root, null)

        /**
         * A parent path component.
         */
        @JvmStatic @get:JvmName("parent") val parent = PathComponent(PathComponentKind.Parent, null)

        /**
         * A current path component.
         */
        @JvmStatic @get:JvmName("current") val current = PathComponent(PathComponentKind.Current, null)

    }

    /**
     * Gets whether the component is a special component.
     */
    val isSpecial
        get() = this.kind != PathComponentKind.File && this.kind != PathComponentKind.Folder

    override fun toString(): String {
        return when (this.kind) {
            PathComponentKind.Folder -> "$name/"
            PathComponentKind.File -> this.name!!
            PathComponentKind.Root -> "" + Path.pathSeparator
            PathComponentKind.Current -> "." + Path.pathSeparator
            PathComponentKind.Parent -> ".." + Path.pathSeparator
        }
    }
}