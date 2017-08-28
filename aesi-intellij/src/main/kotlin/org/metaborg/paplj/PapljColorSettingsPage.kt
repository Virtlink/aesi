package org.metaborg.paplj

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import org.metaborg.paplj.lexer.PapljSyntaxHighlighter
import org.metaborg.paplj.lexer.PapljTextColor
import javax.swing.Icon

class PapljColorSettingsPage : ColorSettingsPage {

    private val ATTRIBUTE_DESCRIPTORS = PapljTextColor.values().map { it.attributesDescriptor }.toTypedArray()

    private val ANNOTATOR_DESCRIPTORS = null

    override fun getDisplayName() = "PAPLJ"

    override fun getIcon() = PapljIcons.FILE

    override fun getHighlighter() = PapljSyntaxHighlighter()

    override fun getAdditionalHighlightingTagToDescriptorMap() = ANNOTATOR_DESCRIPTORS

    override fun getAttributeDescriptors() = ATTRIBUTE_DESCRIPTORS

    override fun getColorDescriptors() = ColorDescriptor.EMPTY_ARRAY

    override fun getDemoText(): String {
        return "# You are reading the \".properties\" entry.\n" +
                "! The exclamation mark can also mark text as comments.\n" +
                "website = http://en.wikipedia.org/\n" +
                "language = English\n" +
                "# The backslash below tells the application to continue reading\n" +
                "# the value onto the next line.\n" +
                "message = Welcome to \\\n" +
                "          Wikipedia!\n" +
                "# Add spaces to the key\n" +
                "key\\ with\\ spaces = This is the value that could be looked up with the key \"key with spaces\".\n" +
                "# Unicode\n" +
                "tab : \\u0009";
    }

}