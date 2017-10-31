package com.virtlink.editorservices.vfs

import java.net.URI

abstract class Resource(
        override val name: String,
        override val parent: IFolder)
    : IResource {

    override val uri: URI
        get() = URI("aesi://$path")
    override val project: IProject
        get() = this.parent.project
    override val workspace: IWorkspace
        get() = this.parent.workspace

    override fun resolve(path: String): IResource? {
        return PathUtils.resolve(Path(path), this)
    }

    override fun find(selector: (r: IResource) -> Boolean, filter: (f: IFolder) -> Boolean, depthFirst: Boolean): List<IResource> {
        return PathUtils.find(selector, filter, depthFirst, this)
    }

    abstract override val path: String
    abstract override val exists: Boolean
    abstract override fun refresh()

}