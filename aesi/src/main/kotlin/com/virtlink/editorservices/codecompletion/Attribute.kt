package com.virtlink.editorservices.codecompletion

@Deprecated("Removed")
enum class Attribute {
    /**
     * The member is external to this project.
     */
    External,
    /**
     * The member is deprecated/obsolete.
     */
    Deprecated,
    /**
     * The member is excluded from compilation for some reason.
     */
    Excluded,
    /**
     * The member defines a test, test suite, test library.
     */
    Test,
    /**
     * The member is defined in the current type.
     */
    NotInherited,
    /**
     * The member is static.
     */
    Static,
}