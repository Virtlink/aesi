package com.virtlink.paplj.intellij

import com.intellij.lang.Language
import com.virtlink.editorservices.intellij.AesiModule
import com.virtlink.editorservices.codecompletion.ICodeCompleter
import com.virtlink.editorservices.referenceresolution.IReferenceResolver
import com.virtlink.editorservices.structureoutline.IStructureOutliner
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.virtlink.paplj.codecompletion.DummyCodeCompleter
import com.virtlink.paplj.referenceresolution.DummyReferenceResolver
import com.virtlink.paplj.syntaxcoloring.AntlrSyntaxColorizer
import com.virtlink.paplj.structureoutline.DummyStructureOutliner


class PapljModule : AesiModule() {

    override fun configure() {
        bind(Language::class.java).toInstance(PapljLanguage)
        bind(IReferenceResolver::class.java).to(DummyReferenceResolver::class.java)
        bind(IStructureOutliner::class.java).to(DummyStructureOutliner::class.java)
        super.configure()
    }

    override fun bindCodeCompletion() {
        bind(ICodeCompleter::class.java).to(DummyCodeCompleter::class.java)
        super.bindCodeCompletion()
    }

    override fun bindSyntaxColoring() {
        bind(ISyntaxColorer::class.java).to(AntlrSyntaxColorizer::class.java)
//        bind(ISyntaxColorer::class.java).to(DummySyntaxColorer::class.java)
        super.bindSyntaxColoring()
    }
}