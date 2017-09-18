package com.virtlink.editorservices.syntaxcoloring

interface ISyntaxColorerFactory {
    fun create() : ISyntaxColorer
}