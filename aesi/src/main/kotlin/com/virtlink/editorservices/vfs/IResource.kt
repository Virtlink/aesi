package com.virtlink.editorservices.vfs

import java.net.URI

/**
 * A resource.
 */
interface IResource {

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
     * Gets the folder that contains this resource;
     * or null when the resource has no parent
     * (i.e. is a project folder itself).
     */
    val parent: IFolder?

    /**
     * Gets the project that contains this resource.
     */
    val project: IProject

    /**
     * Gets the workspace that contains this resource.
     */
    val workspace: IWorkspace

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