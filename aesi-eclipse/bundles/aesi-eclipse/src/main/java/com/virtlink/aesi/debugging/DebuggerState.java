package com.virtlink.aesi.debugging;

/**
 * Specifies the state of the debugger.
 */
public enum DebuggerState {
    /**
     * The debugger has been loaded but is not yet initialized.
     */
    Loaded,
    /**
     * The debugger has been initialized, but no program has been attached yet.
     */
    Ready,
    /**
     * The attached program is running (or suspended).
     */
    Running,
    /**
     * The debugger has been terminated.
     */
    Terminated,
}
