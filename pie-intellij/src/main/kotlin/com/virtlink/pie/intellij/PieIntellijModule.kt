package com.virtlink.pie.intellij

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import com.intellij.lang.Language
import com.virtlink.dummy.DummyCodeCompletionBuilder
import com.virtlink.editorservices.intellij.AesiIntellijModule
import com.virtlink.pie.PieModule
import com.virtlink.pie.codecompletion.PieCodeCompletionService


class PieIntellijModule : AbstractModule() {

    override fun configure() {
        install(AesiIntellijModule())
        install(PieModule())

        bind(Language::class.java).toInstance(PieLanguage)
    }
}