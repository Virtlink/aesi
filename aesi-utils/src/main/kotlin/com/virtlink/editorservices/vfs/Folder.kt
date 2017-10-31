package com.virtlink.editorservices.vfs

abstract class Folder(
        override val name: String,
        override val parent: IFolder)
    : Resource(name, parent), IFolder {

    override val path: String
        get() = Path(this.parent.path).append(Path(PathComponent.file(this.name))).toString()

    abstract override val children: List<IResource>
    abstract override val exists: Boolean
    abstract override fun refresh()

}