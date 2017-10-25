package org.slf4j.impl;

import org.eclipse.core.runtime.Status;

/**
 * Specifies the severity of a log message.
 */
public enum Severity {
    ERROR(40, "ERROR"),
    WARN(30, "WARN"),
    INFO(20, "INFO"),
    DEBUG(10, "DEBUG"),
    TRACE(0, "TRACE");

    private int levelInt;
    private String levelStr;

    private Severity(int i, String s) {
        this.levelInt = i;
        this.levelStr = s;
    }

    public int toInt() {
        return this.levelInt;
    }

    /**
     * Converts the severity to the severity of a status message.
     * @return The status message severity level.
     */
    public int toStatusInt() {
        switch (this) {
            case ERROR: return Status.ERROR;
            case WARN: return Status.WARNING;
            case INFO: return Status.INFO;
            case DEBUG:
            case TRACE:
            default: return Status.OK;
        }
    }

    public String toString() {
        return this.levelStr;
    }
}
