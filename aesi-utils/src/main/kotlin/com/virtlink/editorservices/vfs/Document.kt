package com.virtlink.editorservices.vfs

import com.virtlink.editorservices.resources.IContent
import java.net.URI

abstract class Document(
        override val name: String,
        override val parent: IFolder)
    : Resource(name, parent), IDocument {

    override val content: IDocumentContent
        get() = TODO("not implemented")

    override fun resolve(path: String): IResource? {
        TODO("not implemented")
    }

    override fun find(selector: (r: IResource) -> Boolean, filter: (f: IFolder) -> Boolean, depthFirst: Boolean): List<IResource> {
        TODO("not implemented")
    }

    override fun close() {
        TODO("not implemented")
    }

}