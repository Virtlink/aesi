package com.virtlink.editorservices.lsp.content

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.content.IContent
import com.virtlink.editorservices.content.TextChange
import java.io.LineNumberReader

/**
 * Content with an associated version number.
 */
class VersionedContent(private var content: IContent, val version: Int): IContent {

    override val length: Int
        get() = this.content.length

    override val lineCount: Int
        get() = this.content.lineCount

    override fun createReader(): LineNumberReader
            = this.content.createReader()


    override fun getOffset(position: Position): Offset?
            = this.content.getOffset(position)

    override fun getPosition(offset: Offset): Position?
            = this.content.getPosition(offset)

    override fun withChanges(changes: List<TextChange>): VersionedContent
            = withChanges(changes, this.version)

    fun withChanges(changes: List<TextChange>, newVersion: Int): VersionedContent {
        val newContent = this.content.withChanges(changes)
        return VersionedContent(newContent, newVersion)
    }
}