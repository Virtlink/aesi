package com.virtlink.editorservices.vfs

import java.net.URI

abstract class Project(
        override val name: String,
        override val workspace: IWorkspace)
    : IProject {

    override val path: String
        get() = Path(PathComponent.root, PathComponent.folder(this.name)).toString()
    override val uri: URI
        get() = URI("aesi://$path")
    override val exists: Boolean
        get() = true
    override val parent: IFolder?
        get() = null
    override val project: IProject
        get() = this

    override fun resolve(path: String): IResource? {
        return PathUtils.resolve(Path(path), this)
    }

    override fun find(selector: (r: IResource) -> Boolean, filter: (f: IFolder) -> Boolean, depthFirst: Boolean): List<IResource> {
        return PathUtils.find(selector, filter, depthFirst, this)
    }

    override fun toString(): String = this.path

    abstract override val children: List<IResource>
    abstract override fun refresh()

}