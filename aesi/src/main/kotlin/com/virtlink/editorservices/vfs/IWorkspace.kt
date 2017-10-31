package com.virtlink.editorservices.vfs

/**
 * A workspace.
 */
interface IWorkspace {

    /**
     * Gets the projects in this workspace.
     */
    val projects: List<IProject>

}