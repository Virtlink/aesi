package org.metaborg.paplj

import com.intellij.ide.ApplicationLoadListener
import com.intellij.lang.PsiBuilderFactory
import com.intellij.openapi.application.Application
import com.intellij.testFramework.registerServiceInstance
import org.metaborg.paplj.syntaxhighlighting.ExtendedPsiBuilderFactoryImpl

class PapljPluginInitializer : ApplicationLoadListener {

    override fun beforeApplicationLoaded(application: Application, configPath: String) {
        // Override the PsiBuilderFactory implementation with our own,
        // which passes the PsiFile object to the createLexer() method.
        application.registerServiceInstance(PsiBuilderFactory::class.java, ExtendedPsiBuilderFactoryImpl())
    }
}