package com.virtlink.editorservices.intellij

import java.net.URI

/**
 * A dummy project that is returned when the project
 * a file belongs to cannot be determined.
 */
@Deprecated("Replaced by Project")
object DummyProject {
    override val uri: URI
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}