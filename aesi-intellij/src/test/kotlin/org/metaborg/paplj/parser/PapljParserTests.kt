package org.metaborg.paplj.parser

class PapljParserTests : PapljParserTestsBase() {

    fun testBinTreeProgram() {
        // Parse the file BinTreeProgram.pj
        // and compare the resulting PSI tree to BinTreeProgram.txt
        doTest(true)
    }

}