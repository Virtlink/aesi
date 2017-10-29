package com.virtlink.paplj.intellij

import com.google.inject.AbstractModule
import com.intellij.lang.Language
import com.virtlink.editorservices.intellij.AesiIntellijModule
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.referenceresolution.IReferenceResolverService
import com.virtlink.editorservices.structureoutline.IStructureOutlineService
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService
import com.virtlink.paplj.PapljModule
import com.virtlink.paplj.codecompletion.DummyCodeCompleter
import com.virtlink.paplj.referenceresolution.DummyReferenceResolver
import com.virtlink.paplj.syntaxcoloring.AntlrSyntaxColorizer
import com.virtlink.paplj.structureoutline.DummyStructureOutliner


class PapljIntellijModule : AbstractModule() {

    override fun configure() {
        install(AesiIntellijModule())
        install(PapljModule())

        bind(Language::class.java).toInstance(PapljLanguage)
    }
}