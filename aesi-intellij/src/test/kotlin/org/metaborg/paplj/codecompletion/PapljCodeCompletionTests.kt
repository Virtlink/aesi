package org.metaborg.paplj.codecompletion

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

class PapljCodeCompletionTests : LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String  = "testData"

    fun testCompletion() {
        myFixture.configureByFiles("codecompletion/BinTreeProgram.pj")
        // To mode the caret:
//        myFixture.editor.caretModel.moveToOffset(10)
        myFixture.complete(CompletionType.BASIC, 1)
        val lookupStrings = myFixture.lookupElementStrings
        val expectedStrings = listOf("Hello", "if", "Local variable", "Method")
        assertTrue(lookupStrings!!.containsAll(expectedStrings))
        assertEquals(expectedStrings.size, lookupStrings.size)
    }

}