package com.virtlink.pie.intellij.referenceresolution

import com.virtlink.editorservices.intellij.referenceresoluton.AesiReferenceContributor
import com.virtlink.editorservices.intellij.referenceresoluton.AesiReferenceProvider
import com.virtlink.pie.intellij.PiePlugin

class PieReferenceContributor : AesiReferenceContributor(
        PiePlugin.default.injector.getInstance(AesiReferenceProvider::class.java)
)