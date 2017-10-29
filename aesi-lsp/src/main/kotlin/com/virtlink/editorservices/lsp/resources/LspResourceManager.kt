package com.virtlink.editorservices.lsp.resources

import com.google.inject.Inject
import com.virtlink.editorservices.lsp.content.DocumentContentManager
import com.virtlink.editorservices.resources.IContent
import com.virtlink.editorservices.resources.IResourceManager
import java.io.File
import java.net.URI

/**
 * The LSP resource manager.
 */
@Suppress("PrivatePropertyName")
class LspResourceManager @Inject constructor(
        private val documentContentManager: DocumentContentManager)
    : IResourceManager {

    // An URI starts with the project name (always `project` in our case)
    // followed by the project-relative path to the file or folder.
    // For example:
    // lsp:///project/!/folder/filename

    private val LSP_SCHEME: String = "lsp"

    /**
     * The project URI; or null when no project is opened.
     */
    private var projectUri: URI? = null

    /**
     * Opens the project with the specified project URI.
     *
     * @param uri The base URI of files in the project.
     */
    fun openProject(uri: URI) {
        if (this.projectUri != null) {
            throw IllegalStateException("There is already an open project: $projectUri")
        }
        if (!uri.isAbsolute) {
            throw IllegalArgumentException("The project URI must be absolute: $uri")
        }
        this.projectUri = uri
    }

    fun closeProject() {
        this.projectUri = null
    }

    /**
     * Gets the file from the URI.
     *
     * @param uri The URI.
     * @return The file; or null when it could not be determined.
     */
    fun getFile(uri: URI): File? {
        if (uri.scheme != LSP_SCHEME) return null
        val projectUri = this.projectUri ?: return null

        val projectPathSeparator = uri.path.indexOf('!')
        if (projectPathSeparator >= 0) {
            val projectName = uri.path.substring(0, projectPathSeparator).trim('/')
            val path = uri.path.substring(projectPathSeparator + 1).trimStart('/')
            return File(projectUri.resolve(URI(path)).path)
        } else {
            return null
        }
    }

    /**
     * Gets the URI from a file.
     *
     * @param file The file.
     * @return The URI; or null when it could not be determined.
     */
    fun getUri(file: File): URI? {
        val projectUri = this.projectUri ?: return null
        val fileUri = projectUri.relativize(file.toURI())
        if (fileUri.isAbsolute) {
            // The project is not an ancestor of this file.
            return null
        }

        val projectName = "project"
        return URI("$LSP_SCHEME:///$projectName!/${fileUri.path}")
    }

    private fun fixJavaUri(uri: URI): URI {
        return if (uri.scheme == "file" && !uri.schemeSpecificPart.startsWith("//")) {
            URI(uri.scheme + "://" + uri.schemeSpecificPart)
        } else {
            uri
        }
    }

    /**
     * Translates an LSP URI to an AESI URI.
     */
    fun getUriFromLSPUri(lspUri: String): URI {
        return getUri(File(URI(lspUri)))!!
    }

    override fun getProjectOf(uri: URI): URI? {
        if (getFile(uri) == null) return null

        val projectName = "project"
        return URI("$LSP_SCHEME:///$projectName/")
    }

    override fun isProject(uri: URI): Boolean {
        return uri.toString() == "$LSP_SCHEME:///project/"
    }

    override fun isFolder(uri: URI): Boolean {
        return getFile(uri)?.isDirectory ?: false
    }

    override fun isFile(uri: URI): Boolean {
        return getFile(uri)?.isFile ?: false
    }

    override fun exists(uri: URI): Boolean {
        return getFile(uri)?.exists() ?: false
    }

    override fun getChildren(uri: URI): Iterable<URI>? {
        val file = getFile(uri) ?: return null
        return file.listFiles().mapNotNull { f -> getUri(f) }
    }

    override fun getContent(uri: URI): IContent? {
        return this.documentContentManager.getLatestContent(uri)
    }

}