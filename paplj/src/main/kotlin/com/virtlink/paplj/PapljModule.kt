package com.virtlink.paplj

import com.google.inject.Singleton
import com.virtlink.editorservices.AesiBaseModule
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.referenceresolution.IReferenceResolverService
import com.virtlink.editorservices.structureoutline.IStructureOutlineService
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService
import com.virtlink.paplj.codecompletion.DummyCodeCompleter
import com.virtlink.paplj.referenceresolution.DummyReferenceResolver
import com.virtlink.paplj.structureoutline.DummyStructureOutliner
import com.virtlink.paplj.syntaxcoloring.DummySyntaxColorer

class PapljModule: AesiBaseModule() {

    override fun configureCodeCompletion() {
        bind(ICodeCompletionService::class.java).to(DummyCodeCompleter::class.java).`in`(Singleton::class.java)
    }

    override fun configureReferenceResolver() {
        bind(IReferenceResolverService::class.java).to(DummyReferenceResolver::class.java).`in`(Singleton::class.java)
    }

    override fun configureStructureOutline() {
        bind(IStructureOutlineService::class.java).to(DummyStructureOutliner::class.java).`in`(Singleton::class.java)
    }

    override fun configureSyntaxColoring() {
        bind(ISyntaxColoringService::class.java).to(DummySyntaxColorer::class.java).`in`(Singleton::class.java)
    }

}