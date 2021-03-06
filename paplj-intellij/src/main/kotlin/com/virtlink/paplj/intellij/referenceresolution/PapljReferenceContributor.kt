package com.virtlink.paplj.intellij.referenceresolution

import com.virtlink.editorservices.intellij.referenceresoluton.AesiReferenceContributor
import com.virtlink.editorservices.intellij.referenceresoluton.AesiReferenceProvider
import com.virtlink.paplj.intellij.PapljPlugin

class PapljReferenceContributor : AesiReferenceContributor(
        PapljPlugin.default.injector.getInstance(AesiReferenceProvider::class.java)
)