package org.metaborg.paplj.parser

import com.intellij.lang.ParserDefinition
import com.intellij.testFramework.ParsingTestCase
import org.metaborg.paplj.PapljFileType

abstract class PapljParserTestsBase(dataPath: String = "parser",
                                    fileExt: String = PapljFileType.EXTENSION,
                                    vararg definitions: ParserDefinition = arrayOf(PapljParserDefinition()))
    : ParsingTestCase(dataPath, fileExt, *definitions) {

    override fun getTestDataPath(): String = "testData"

    override fun skipSpaces(): Boolean  = true

    override fun includeRanges(): Boolean = true

}