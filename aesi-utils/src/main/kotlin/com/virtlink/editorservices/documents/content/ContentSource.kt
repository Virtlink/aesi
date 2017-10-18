package com.virtlink.editorservices.documents.content

import com.virtlink.editorservices.*
import org.slf4j.LoggerFactory
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Base class for content sources.
 *
 * @property document The document.
 * @property content The initial source content; or null.
 */
abstract class ContentSource(override val document: IDocument, content: IDocumentContent? = null) : IContentSource {

    private val logger = LoggerFactory.getLogger(ContentSource::class.java)

    private var latestContent: IDocumentContent? = content

    protected var lock = ReentrantReadWriteLock()

    override fun invalidate() {
        this.lock.write {
            this.latestContent = null
            logger.info("$document: Content source invalidated")
        }
    }

    override fun getLatestContent(): IDocumentContent {
        this.lock.read {
            var content = this.latestContent
            if (content == null) {
                content = retrieveContent()
                this.lock.write {
                    this.latestContent = content
                    logger.info("$document: Content source retrieved")
                }
            }
            return content
        }
    }

    override fun update(changes: List<DocumentChange>): IDocumentContent? {
        this.lock.read {
            val content = getLatestContent()
            val newContent = content.update(changes)
            this.lock.write {
                val latestContent = getLatestContent()
                if (content != latestContent) {
                    logger.warn("$document: Content source updates ignored; not latest content")
                    return null
                }
                this.latestContent = newContent
                logger.info("$document: Content source updated")
                return newContent
            }
        }
    }

    protected abstract fun retrieveContent(): IDocumentContent

}