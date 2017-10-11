package com.virtlink.logging

import com.xenomachina.text.clear
import org.slf4j.Logger
import java.io.Writer

/**
 * Writes lines to the logger.
 */
class LogWriter(private val logger: Logger, private val logLevel: LogLevel, bufferSize: Int = 1024)
    : Writer(Any()) {

    private var closed = false
    private val buffer = StringBuilder(bufferSize)
    private var inCrNewline = false

    override fun write(cbuf: CharArray, off: Int, len: Int) {
        synchronized (lock) {
            if (this.closed) throw IllegalStateException("The writer was closed.")

            for (i in 0 until len) {
                val c = cbuf[off + i]

                if (this.inCrNewline) {
                    this.inCrNewline = false
                    if (c == '\n') {
                        // CRLF
                        continue
                    }
                }

                if (c == '\r' || c == '\n') {
                    // CR or LF, or maybe CRLF
                    this.inCrNewline = (c == '\r')
                    log(this.buffer.toString())
                    this.buffer.clear()
                    continue
                }

                this.buffer.append(c)
            }
        }
    }

    override fun flush() {
        synchronized (lock) {
            if (this.closed) throw IllegalStateException("The writer was closed.")

            // Nothing to do.
        }
    }

    override fun close() {
        synchronized (lock) {
            this.closed = true
        }
    }

    private fun log(message: String) {
        when (this.logLevel) {
            LogWriter.LogLevel.Trace -> this.logger.trace(message)
            LogWriter.LogLevel.Debug -> this.logger.debug(message)
            LogWriter.LogLevel.Info -> this.logger.info(message)
            LogWriter.LogLevel.Warning -> this.logger.warn(message)
            LogWriter.LogLevel.Error -> this.logger.error(message)
        }
    }

    enum class LogLevel {
        Trace,
        Debug,
        Info,
        Warning,
        Error
    }

}