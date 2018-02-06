package com.virtlink.editorservices.lsp.content

import com.virtlink.editorservices.content.*
import com.virtlink.editorservices.resources.IContent
import com.virtlink.editorservices.resources.TextChange
import com.virtlink.logging.logger
import java.net.URI
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * A content source whose changes are provided by an external entity, such as an editor.
 */
class RemoteContentSource {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    /** Lock */
    private val lock = ReentrantReadWriteLock()

    /** Map with the latest content of each attached document. */
    private val documents: MutableMap<URI, IContent> = mutableMapOf()

    fun getLatest(documentUri: URI): IContent {
        this.lock.read {
            return documents[documentUri]
                    ?: throw IllegalArgumentException("The document '$documentUri' is not known by this source.")
        }
    }

    /**
     * Attaches the specified document to this content source
     * so that its changes are tracked.
     *
     * @param documentUri The document AESI URI to attach.
     * @param text The text of the document.
     * @param version The version of the document.
     */
    fun attach(documentUri: URI, text: String, version: Long) {
        this.lock.write {
            this.documents.put(documentUri, LineContent(text, version))
            LOG.info("$documentUri: Attached with ${text.length} characters and version $version")
        }
    }

    fun detach(documentUri: URI) {
        this.lock.write {
            this.documents.remove(documentUri)
            LOG.info("$documentUri: Detached")
        }
    }

    /**
     * Updates the document with the specified changes.
     *
     * @param document The document AESI URI being updated.
     * @param currentStamp The stamp of the document being updated.
     * @param changes The changes to the document's content.
     * @param newStamp The new stamp of the document after the changes have been applied.
     */
    fun update(documentUri: URI, currentStamp: Long, changes: List<TextChange>, newStamp: Long) {
        this.lock.write {
            this.documents.compute(documentUri, { d, c ->
                if (c == null) {
                    throw IllegalArgumentException("The document '$d' is not known by this source.")
                }
                if (c.lastModificationStamp != currentStamp) {
                    throw IllegalStateException("Expected to update document '$d' with stamp $currentStamp, " +
                            "but found stamp ${c.lastModificationStamp}.")
                }

                val newContent = c.withChanges(changes, newStamp)
                LOG.info("$documentUri: Updated with ${changes.size} changes to stamp $newStamp")
                newContent
            })
        }
    }

}