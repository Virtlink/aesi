package com.virtlink.editorservices.content

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import java.io.LineNumberReader

/**
 * Empty content.
 */
class EmptyContent: IContent {
    override val length: Int
        get() = 0
    override val lineCount: Int
        get() = 1

    override fun createReader(): LineNumberReader {
        TODO("not implemented")
    }

    override fun getOffset(position: Position): Offset?
            = if (position == Position(0, 0)) Offset(0) else null

    override fun getPosition(offset: Offset): Position?
            = if (offset.offset == 0) Position(0, 0) else null

    override fun withChanges(changes: List<TextChange>): IContent
            = StringContent("").withChanges(changes)
}