package com.virtlink.editorservices.vfs

/**
 * A workspace.
 */
abstract class Workspace: IWorkspace {

    abstract override val projects: List<IProject>

}