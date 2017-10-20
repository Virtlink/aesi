package com.virtlink.editorservices.lsp

/**
 * Factory for [DocumentManager] objects.
 */
interface IDocumentManagerFactory {

    /**
     * Creates a new document manager for the specified project.
     *
     * @param project The project.
     * @return The document manager.
     */
    fun create(project: Project): DocumentManager

}