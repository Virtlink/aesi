package com.virtlink.aesi.debugging;

import java.util.EventObject;

/**
 * A suspend/resume thread event.
 */
public class SuspendThreadEvent extends EventObject {

    private final IAesiThread thread;
    private final SuspendEventCause cause;

    /**
     * Gets the thread on which the event occurred.
     *
     * @return The thread.
     */
    public IAesiThread getThread() { return this.thread; }

    /**
     * Gets the cause of the event.
     *
     * @return A member of the {@link SuspendEventCause} enum.
     */
    public SuspendEventCause getCause() { return this.cause; }

    /**
     * Initializes a new instance of the {@link SuspendThreadEvent} class.
     *
     * @param source The source object of the event.
     * @param thread The thread on which the event occurred.
     * @param cause The cause of the event.
     * @throws IllegalArgumentException if source is null.
     */
    public SuspendThreadEvent(Object source, IAesiThread thread, SuspendEventCause cause) {
        super(source);

        this.thread = thread;
        this.cause = cause;
    }
}
