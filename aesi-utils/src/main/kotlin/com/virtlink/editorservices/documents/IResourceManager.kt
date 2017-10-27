package com.virtlink.editorservices.documents

import com.virtlink.editorservices.content.IContent
import java.net.URI

/**
 * Manages the files and folders.
 *
 * This manager tracks changes to files.
 * A language implementation can query the manager for files,
 * resolve a path into a file, or get the content of a file.
 *
 * The URI's of a file or folder have a prescribed format:
 * 1. A folder URI ends with a slash, for example `file:///home/user/myfolder/`.
 * 2. A file URI never ends with a slash, for example `file:///home/user/myfile`.
 * 3. A file or folder associated with a project has the project URI followed by an exclamation mark `!`
 *    followed by the file or folder path. For example `file:///home/user/myproject/!/myfile`.
 * 4. A file that is not associated with any project or local location (e.g. `file`) or remote location (e.g. `http`)
 *    gets the `mem` scheme. For example `mem:///myfile`.
 *
 * A file or folder URI need not be pointing to an existing file.
 */
interface IResourceManager {

    /**
     * Gets the project that contains the resource with the specified URI.
     *
     * When the resource is a project, it returns its own URI.
     *
     * @param uri The URI of the file or folder.
     * @return The URI of the project; or null when the resource is not part of a project.
     */
    fun getProject(uri: URI): URI?

    /**
     * Gets whether the resource is a project.
     *
     * @param uri The URI of the resource.
     * @return True when the resource is a project; otherwise, false.
     */
    fun isProject(uri: URI): Boolean

    /**
     * Gets whether the resource is a folder.
     *
     * @param uri The URI of the resource.
     * @return True when the resource is a folder; otherwise, false.
     */
    fun isFolder(uri: URI): Boolean

    /**
     * Gets whether the resource is a file.
     *
     * @param uri The URI of the resource.
     * @return True when the resource is a file; otherwise, false.
     */
    fun isFile(uri: URI): Boolean

    /**
     * Gets whether the resource with the specified URI currently exists.
     *
     * Usually you don't need to call this method. Instead, use the other
     * methods directly and check the result for null. It is, after all,
     * entirely possible that a file that existed when the call to [exists] was
     * made ceased to exist when the subsequent call to [getContent]  is made.
     *
     * @param uri The URI of the resource.
     * @return True when it currently accessible and exists; otherwise, false.
     */
    fun exists(uri: URI): Boolean

    /**
     * Gets the children of the folder or project with the specified URI.
     *
     * @param uri The URI of the folder or project.
     * @return The URIs of the files and folders in the specified folder;
     * or null when the resource is not currently accessible or does not currently exist.
     */
    fun getChildren(uri: URI): Iterable<URI>?

    /**
     * Gets the content of the file with the specified URI.
     *
     * @return The content of the file; or null when it is not currently accessible
     * or does not currently exist.
     */
    fun getContent(uri: URI): IContent?

}