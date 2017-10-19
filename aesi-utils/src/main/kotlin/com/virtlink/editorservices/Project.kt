package com.virtlink.editorservices

import com.google.inject.Inject
import java.net.URI

data class Project @Inject constructor(
        override val uri: URI): IProject {

    override fun toString(): String
        = this.uri.toString()

}