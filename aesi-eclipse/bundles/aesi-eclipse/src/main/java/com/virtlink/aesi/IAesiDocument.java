package com.virtlink.aesi;

import java.net.URI;

/**
 * A document.
 *
 * If the same file exists in two different projects, it is represented
 * by two different instances of {@link IAesiDocument}.
 */
public interface IAesiDocument {

    /**
     * Gets the resource identifier of the document.
     *
     * The document may or may not exist on disk. For example, this may represent
     * a document that has only an in-memory representation.
     *
     * @return The document's URI.
     */
    URI getUri();

    /**
     * Gets the project to which the document belongs.
     *
     * @return The document's project.
     */
    IAesiProject getProject();

}
