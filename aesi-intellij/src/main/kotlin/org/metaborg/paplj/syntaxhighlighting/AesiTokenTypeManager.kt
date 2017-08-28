package org.metaborg.paplj.syntaxhighlighting

import com.google.common.cache.CacheLoader
import com.google.common.cache.CacheBuilder
import com.google.common.cache.LoadingCache


/**
 * Tracks token types.
 *
 *
 * IntelliJ can't handle too many token types. However, we'll still need a different token type for
 * each different style. This class returns a token type that matches the style, if found; otherwise,
 * creates a new token type and stores it for re-use.

 * The token type manager is specific to a single language.
 */
class AesiTokenTypeManager {

    private val tokenCache: LoadingCache<String, AesiTokenType>

    val defaultTokenType
        get() = getTokenType(defaultScope)

    init {
        this.tokenCache = CacheBuilder.newBuilder().weakValues()
                .build(object : CacheLoader<String, AesiTokenType>() {
                    @Throws(Exception::class)
                    override fun load(scope: String): AesiTokenType {
                        return AesiTokenType(scope)
                    }
                })
    }

    fun getTokenType(scope: String?): AesiTokenType {
        return this.tokenCache.getUnchecked(scope ?: defaultScope)
    }

    val defaultScope: String
        get() = "text"
}