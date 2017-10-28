package com.virtlink.editorservices.content

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.resources.IContent
import com.virtlink.editorservices.resources.TextChange
import java.io.LineNumberReader

/**
 * Empty content.
 */
class EmptyContent : IContent {
    override val stamp: Long
        get() = 0
    override val text: String
        get() = ""
    override val length: Int
        get() = 0
    override val lineCount: Int
        get() = 1

    override fun createReader(): LineNumberReader {
        TODO("not implemented")
    }

    override fun getOffset(position: Position): Offset?
            = if (position == Position(0, 0)) 0L else null

    override fun getPosition(offset: Offset): Position?
            = if (offset == 0L) Position(0, 0) else null

    override fun withChanges(changes: List<TextChange>, newStamp: Long): IContent
            = StringContent.empty.withChanges(changes, newStamp)
}