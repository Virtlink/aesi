package org.metaborg.paplj

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import org.metaborg.paplj.psi.PapljFile

/**
 * Manages the PAPLJ documents.
 */
object PapljDocumentManager {

    fun toAesiDocument(file: PapljFile): IDocument {
        return toAesiDocument(file.originalFile.virtualFile)
    }

    fun toAesiDocument(file: VirtualFile): IDocument {
        return AesiDocument(file)
    }

    private data class AesiDocument(val file: VirtualFile): IDocument {
        override val path: String
            get() = file.path
    }

}