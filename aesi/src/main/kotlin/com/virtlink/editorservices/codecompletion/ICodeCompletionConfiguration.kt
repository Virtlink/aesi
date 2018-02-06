package com.virtlink.editorservices.codecompletion

/**
 * Configuration of the [ICodeCompletionService].
 */
interface ICodeCompletionConfiguration {

    /**
     * Whether the service supports snippets.
     */
    val supportsSnippets: Boolean

}