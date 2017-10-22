package com.virtlink.editorservices.vfs

import java.net.URI

/**
 * A simple text-based document.
 */
class Document2(
        override val uri: URI,
        override val version: Int,
        override val text: String)
    : IDocument2 {

    override val length: Int
        get() = this.text.length

    override val lines: LineList = LineList.create(text)
}