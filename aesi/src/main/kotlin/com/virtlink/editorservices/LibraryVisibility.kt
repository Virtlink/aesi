package com.virtlink.editorservices

@Deprecated("Removed")
enum class LibraryVisibility {

    /**
     * Visible in any library.
     */
    Public,
    /**
     * Visible in friend libraries.
     */
    Friend,
    /**
     * Visible in the same library.
     */
    Internal,
}
