package com.virtlink.editorservices

import com.google.inject.Inject
import com.virtlink.editorservices.documents.IDocumentManagerFactory
import java.net.URI

class Project @Inject constructor(
        override val uri: URI,
        private val documentManagerFactory: IDocumentManagerFactory): IProject {

    val documents = this.documentManagerFactory.create(this)

    override fun toString(): String
        = this.uri.toString()

}