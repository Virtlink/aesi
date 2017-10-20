package com.virtlink.editorservices.lsp.content

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.content.IContent
import com.virtlink.editorservices.content.IContentSource
import com.virtlink.editorservices.content.LineContent
import com.virtlink.editorservices.content.TextChange
import com.virtlink.logging.logger
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * A content source whose changes are provided by an external entity, such as an editor.
 */
class RemoteContentSource : IContentSource {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    /** Lock */
    private val lock = ReentrantReadWriteLock()

    /** Map with the latest content of each attached document. */
    private val documents: MutableMap<IDocument, VersionedContent> = mutableMapOf()

    override fun getLatest(document: IDocument): IContent {
        this.lock.read {
            return documents[document]
                    ?: throw IllegalArgumentException("The document '$document' is not known by this source.")
        }
    }

//    override fun attach(document: IDocument)
//            = attach(document, "", 0)

    /**
     * Attaches the specified document to this content source
     * so that its changes are tracked.
     *
     * @param document The document to attach.
     * @param text The text of the document.
     * @param version The version of the document.
     */
    fun attach(document: IDocument, text: String, version: Int) {
        this.lock.write {
            this.documents.put(document, VersionedContent(LineContent(text), version))
            LOG.info("$document: Attached with ${text.length} characters and version $version")
        }
    }

    fun detach(document: IDocument) {
        this.lock.write {
            this.documents.remove(document)
            LOG.info("$document: Detached")
        }
    }

    /**
     * Updates the document with the specified changes.
     *
     * @param document The document being updated.
     * @param currentVersion The version of the document being updated.
     * @param changes The changes to the document's content.
     * @param newVersion The new version of the document after the changes have been applied.
     */
    fun update(document: IDocument, currentVersion: Int, changes: List<TextChange>, newVersion: Int) {
        this.lock.write {
            this.documents.compute(document, { d, c ->
                if (c == null) {
                    throw IllegalArgumentException("The document '$d' is not known by this source.")
                }
                if (c.version != currentVersion) {
                    throw IllegalStateException("Expected to update document '$d' with version $currentVersion, " +
                            "but found version ${c.version}.")
                }

                val newContent = c.withChanges(changes, newVersion)
                LOG.info("$document: Updated with ${changes.size} changes to version $newVersion")
                newContent
            })
        }
    }

}