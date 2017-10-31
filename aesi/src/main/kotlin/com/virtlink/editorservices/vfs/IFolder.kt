package com.virtlink.editorservices.vfs

/**
 * A folder.
 */
interface IFolder: IResource {

    /**
     * Gets the resources in this folder.
     */
    val children: List<IResource>

}