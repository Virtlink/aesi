package com.virtlink.editorservices

import java.io.Serializable

/**
 * A version of a document.
 *
 * @property document The document.
 * @property version The version number.
 */
@Deprecated("Removed")
data class DocumentVersion(
        val document: Document,
        val version: Int)
    : Comparable<DocumentVersion>, Serializable
{

    override fun compareTo(other: DocumentVersion): Int
            = this.version.compareTo(other.version)

    override fun toString(): String
            = "$document#$version"

}