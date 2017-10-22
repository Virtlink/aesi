package com.virtlink.paplj.intellij

import com.intellij.lang.Language
import com.virtlink.editorservices.intellij.AesiModule
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.referenceresolution.IReferenceResolverService
import com.virtlink.editorservices.structureoutline.IStructureOutlineService
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService
import com.virtlink.paplj.codecompletion.DummyCodeCompleter
import com.virtlink.paplj.referenceresolution.DummyReferenceResolver
import com.virtlink.paplj.syntaxcoloring.AntlrSyntaxColorizer
import com.virtlink.paplj.structureoutline.DummyStructureOutliner


class PapljModule : AesiModule() {

    override fun configure() {
        bind(Language::class.java).toInstance(PapljLanguage)
        bind(IReferenceResolverService::class.java).to(DummyReferenceResolver::class.java)
        bind(IStructureOutlineService::class.java).to(DummyStructureOutliner::class.java)
        super.configure()
    }

    override fun bindCodeCompletion() {
        bind(ICodeCompletionService::class.java).to(DummyCodeCompleter::class.java)
        super.bindCodeCompletion()
    }

    override fun bindSyntaxColoring() {
        bind(ISyntaxColoringService::class.java).to(AntlrSyntaxColorizer::class.java)
//        bind(ISyntaxColoringService::class.java).to(DummySyntaxColorer::class.java)
        super.bindSyntaxColoring()
    }
}