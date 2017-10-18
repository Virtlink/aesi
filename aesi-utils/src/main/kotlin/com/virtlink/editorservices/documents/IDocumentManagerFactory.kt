package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IProject

interface IDocumentManagerFactory {
    fun create(project: IProject): IDocumentManager
}