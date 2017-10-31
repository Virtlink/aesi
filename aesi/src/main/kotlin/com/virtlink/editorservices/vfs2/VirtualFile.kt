package com.virtlink.editorservices.vfs2

import com.virtlink.editorservices.vfs.IFolder
import com.virtlink.editorservices.vfs.IProject
import com.virtlink.editorservices.vfs.IResource
import com.virtlink.editorservices.vfs.IWorkspace
import java.net.URI

/**
 * A virtual file or folder.
 */
interface VirtualFile {

    /**
     * Gets the name of the resource.
     *
     * The name includes the file extension, if any.
     */
    val name: String

    /**
     * Gets the full path of the resource.
     *
     * The path starts at the project.
     */
    val path: String

    /**
     * Gets the URI of the resource.
     */
    val uri: URI

    /**
     * Gets whether the resource currently exists.
     */
    val exists: Boolean

    /**
     * Gets whether the resource is a folder.
     */
    val isFolder: Boolean

    /**
     * Gets whether the resource is a file.
     */
    val isFile: Boolean

    /**
     * Gets whether the resource is a project.
     */
    val isProject: Boolean

    /**
     * Gets whether the resource is a workspace.
     */
    val isWorkspace: Boolean

    /**
     * Gets the workspace that contains this resource.
     */
    val workspace: IWorkspace

    /**
     * Gets the project that contains this resource.
     */
    val project: IProject

    /**
     * Gets the folder that contains this resource;
     * or null when the resource has no parent
     * (i.e. is a project folder itself).
     */
    val parent: IFolder?

    /**
     * Gets the resources in this folder;
     * or null when this is not a folder.
     */
    val children: List<IResource>?

    /**
     * Resynchronizes the resource.
     */
    fun refresh()

    /**
     * Resolves a resource relative to this resource.
     *
     * @param path The path to resolve.
     * @return The resource; or null if not found.
     */
    fun resolve(path: String): IResource?

    /**
     * Finds all descendant resources selected by the specified selector function.
     *
     * @param selector The function that determines whether a resource is included in the result.
     * @param filter The function that determines whether a folder is traversed.
     * @param depthFirst When true, performs a depth-first search; otherwise, performs a breath-first search.
     */
    fun find(selector: (r: IResource) -> Boolean, filter: (f: IFolder) -> Boolean, depthFirst: Boolean): List<IResource>

    /**
     * Finds all descendant resources selected by the specified selector function,
     * with depth-first search.
     *
     * @param selector The function that determines whether a resource is included in the result.
     * @param filter The function that determines whether a folder is traversed.
     */
    fun find(selector: (r: IResource) -> Boolean, filter: (f: IFolder) -> Boolean): List<IResource>
            = find(selector, filter, true)

    /**
     * Finds all descendant resources selected by the specified selector function,
     * with depth-first search in all folders.
     *
     * @param selector The function that determines whether a resource is included in the result.
     */
    fun find(selector: (r: IResource) -> Boolean): List<IResource>
            = find(selector, { _ -> true })

}