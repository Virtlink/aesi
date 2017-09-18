package com.virtlink.paplj.intellij.syntaxcoloring

import com.google.inject.Injector
import com.intellij.codeHighlighting.TextEditorHighlightingPassRegistrar
import com.intellij.openapi.project.Project
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiHighlightingPassFactory
import com.virtlink.paplj.intellij.PapljPlugin

class PapljHighlightingPassFactory(
        project: Project,
        registrar: TextEditorHighlightingPassRegistrar)
    : AesiHighlightingPassFactory(project, registrar) {

    override val injector: Injector
        get() = PapljPlugin.getInjector()

}