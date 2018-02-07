package com.virtlink.editorservices

class ScopeNames constructor(
        vararg val scopes: String) {

//    constructor(scopeNames: String)
//            : this(*scopeNames.split(',').map { it.trim() }.filter { it.isNotEmpty() }.toTypedArray())

    /**
     * Determines whether the scopes list contains the specified scope name prefix.
     *
     * @param scopePrefix The scope name prefix to look for.
     * @return True when the scope was found; otherwise, false.
     */
    operator fun contains(scopePrefix: String): Boolean {
        return scopes.any { it.startsWith(scopePrefix, true) }
    }

}