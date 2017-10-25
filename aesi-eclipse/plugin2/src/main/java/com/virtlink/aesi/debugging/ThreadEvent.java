package com.virtlink.aesi.debugging;

import java.util.EventObject;

/**
 * A thread event.
 */
public class ThreadEvent extends EventObject {

    private final IAesiThread thread;

    /**
     * Gets the thread on which the event occurred.
     *
     * @return The thread.
     */
    public IAesiThread getThread() { return this.thread; }

    /**
     * Initializes a new instance of the {@link ThreadEvent} class.
     *
     * @param source The source object of the event.
     * @param thread The thread on which the event occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ThreadEvent(Object source, IAesiThread thread) {
        super(source);

        this.thread = thread;
    }
}
