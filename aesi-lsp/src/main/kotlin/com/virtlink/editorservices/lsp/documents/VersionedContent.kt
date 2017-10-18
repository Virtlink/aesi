package com.virtlink.editorservices.lsp.documents

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.documents.content.DocumentChange
import com.virtlink.editorservices.documents.content.IDocumentContent
/**
 * LSP document content.
 *
 * @property version The version of the content.
 * @property content The actual content.
 */
class VersionedContent(val version: Int, val innerContent: IDocumentContent): IDocumentContent {

    override val length: Int
        get() = this.innerContent.length
    override val text: String
        get() = this.innerContent.text
    override val lineCount: Int
        get() = this.innerContent.lineCount

    override fun getOffset(position: Position): Offset?
            = this.innerContent.getOffset(position)

    override fun getPosition(offset: Offset): Position?
            = this.innerContent.getPosition(offset)

    override fun update(changes: List<DocumentChange>): IDocumentContent
            = this.innerContent.update(changes)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false
        other as VersionedContent
        return this.version == other.version
            && this.innerContent == other.innerContent
    }

    override fun hashCode(): Int {
        var hash = 17
        hash = hash * 23 + this.version.hashCode()
        hash = hash * 23 + this.innerContent.hashCode()
        return hash
    }

    override fun toString(): String
            = this.text
}