package com.virtlink.aesi.debugging;

/**
 * Specifies the state of a thread.
 */
public enum ThreadState {
    /**
     * The thread is initialized.
     */
    Initialized,
    /**
     * The thread is running.
     */
    Running,
    /**
     * The thread is suspended.
     */
    Suspended,
    /**
     * The thread is terminated.
     */
    Terminated,

}
