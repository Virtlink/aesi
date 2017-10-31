package com.virtlink.editorservices.vfs

/**
 * Specifies the kind of path component.
 */
enum class PathComponentKind {
    Folder,
    File,
    Root,
    Current,
    Parent
}