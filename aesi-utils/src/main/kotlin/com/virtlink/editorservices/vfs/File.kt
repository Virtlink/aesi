package com.virtlink.editorservices.vfs

abstract class File(
        name: String,
        parent: IFolder)
    : Resource(name, parent), IFile {

    override val path: String
        get() = Path(this.parent.path).append(Path(PathComponent.file(this.name))).toString()

    abstract override val content: IFileContent
    abstract override val exists: Boolean
    abstract override fun refresh()
}