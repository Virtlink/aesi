package com.virtlink.paplj

import com.virtlink.paplj.parser.AstBuilder
import com.virtlink.paplj.parser.PapljParser
import com.virtlink.paplj.terms.InterningTermFactory
import com.virtlink.paplj.terms.ListTerm
import com.virtlink.paplj.terms.StringTerm
import com.virtlink.paplj.terms.paplj.ImportTerm
import com.virtlink.paplj.terms.paplj.ProgramTerm
import org.junit.Test
import java.io.StringReader
import kotlin.test.assertEquals

class AstTests {

    @Test
    fun parseProgram() {
        // Arrange
        val input = "program x.y.z;"
        val reader = StringReader(input)
        val termFactory = InterningTermFactory()
        termFactory.registerBuilder(ProgramTerm.constructor, { _, cl -> ProgramTerm.create(cl) })
        val parser = PapljParser(AstBuilder(termFactory))

        // Act
        val result = parser.parse(reader)

        // Assert
        val expected = ProgramTerm(StringTerm("x.y.z"), ListTerm())
        assertEquals(expected, result)
    }

    @Test
    fun parseProgramWithImports() {
        // Arrange
        val input =
                "program x.y.z;" +
                "import abc;" +
                "import def.ghi;" +
                "import jkl.*;"
        val reader = StringReader(input)
        val termFactory = InterningTermFactory()
        termFactory.registerBuilder(ProgramTerm.constructor, { _, cl -> ProgramTerm.create(cl) })
        termFactory.registerBuilder(ImportTerm.constructor, { _, cl -> ImportTerm.create(cl) })
        val parser = PapljParser(AstBuilder(termFactory))

        // Act
        val result = parser.parse(reader)

        // Assert
        val expected = ProgramTerm(StringTerm("x.y.z"), ListTerm(
                ImportTerm(StringTerm("abc")),
                ImportTerm(StringTerm("def.ghi")),
                ImportTerm(StringTerm("jkl.*"))
        ))
        assertEquals(expected, result)
    }

}