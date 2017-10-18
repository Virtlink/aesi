package com.virtlink.editorservices.documents

/**
 * A source of document content.
 *
 * A source can be a file on the file system,
 * an in-memory cache, or a remote client.
 * It's up to the content source to perform the
 * necessary steps to return an up-to-date document content.
 */
interface IContentSource {

    fun getContent(): IDocumentContent

}