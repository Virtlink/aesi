package com.virtlink.editorservices

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.NullCodeCompletionService
import com.virtlink.editorservices.codefolding.ICodeFoldingService
import com.virtlink.editorservices.codefolding.NullCodeFoldingService
import com.virtlink.editorservices.documentation.IDocumentationProviderService
import com.virtlink.editorservices.documentation.NullDocumentationProviderService
import com.virtlink.editorservices.referenceresolution.IDefinitionResolverService
import com.virtlink.editorservices.referenceresolution.IReferenceResolverService
import com.virtlink.editorservices.referenceresolution.NullDefinitionResolverService
import com.virtlink.editorservices.referenceresolution.NullReferenceResolverService
import com.virtlink.editorservices.structureoutline.IStructureOutlineService
import com.virtlink.editorservices.structureoutline.NullStructureOutlineService
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService
import com.virtlink.editorservices.syntaxcoloring.NullSyntaxColoringService
import com.virtlink.editorservices.types.ITypeProviderService
import com.virtlink.editorservices.types.NullTypeProviderService

/**
 * Base module for language implementations.
 */
open class AesiBaseModule : AbstractModule() {

    override fun configure() {
        configureCodeCompletion()
        configureCodeFolding()
        configureDocumentationProvider()
        configureDefinitionResolver()
        configureReferenceResolver()
        configureStructureOutline()
        configureSyntaxColoring()
        configureTypeProvider()
    }

    /**
     * Configures the code completion service.
     *
     * The default implementation binds a null service.
     */
    protected open fun configureCodeCompletion() {
        bind(ICodeCompletionService::class.java).to(NullCodeCompletionService::class.java).`in`(Singleton::class.java)
    }

    /**
     * Configures the code folding service.
     *
     * The default implementation binds a null service.
     */
    protected open fun configureCodeFolding() {
        bind(ICodeFoldingService::class.java).to(NullCodeFoldingService::class.java).`in`(Singleton::class.java)
    }

    /**
     * Configures the documentation provider service.
     *
     * The default implementation binds a null service.
     */
    protected open fun configureDocumentationProvider() {
        bind(IDocumentationProviderService::class.java).to(NullDocumentationProviderService::class.java).`in`(Singleton::class.java)
    }

    /**
     * Configures the definition resolver service.
     *
     * The default implementation binds a null service.
     */
    protected open fun configureDefinitionResolver() {
        bind(IDefinitionResolverService::class.java).to(NullDefinitionResolverService::class.java).`in`(Singleton::class.java)
    }

    /**
     * Configures the reference resolver service.
     *
     * The default implementation binds a null service.
     */
    protected open fun configureReferenceResolver() {
        bind(IReferenceResolverService::class.java).to(NullReferenceResolverService::class.java).`in`(Singleton::class.java)
    }

    /**
     * Configures the structure outline service.
     *
     * The default implementation binds a null service.
     */
    protected open fun configureStructureOutline() {
        bind(IStructureOutlineService::class.java).to(NullStructureOutlineService::class.java).`in`(Singleton::class.java)
    }

    /**
     * Configures the syntax coloring service.
     *
     * The default implementation binds a null service.
     */
    protected open fun configureSyntaxColoring() {
        bind(ISyntaxColoringService::class.java).to(NullSyntaxColoringService::class.java).`in`(Singleton::class.java)
    }

    /**
     * Configures the type provider service.
     *
     * The default implementation binds a null service.
     */
    protected open fun configureTypeProvider() {
        bind(ITypeProviderService::class.java).to(NullTypeProviderService::class.java).`in`(Singleton::class.java)
    }
}