package com.virtlink.aesi.debugging;

import javax.annotation.Nullable;
import java.util.EventObject;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Abstract base class for debug sessions.
 */
public abstract class AbstractDebugSession implements IDebugSession {

    @Nullable
    private CopyOnWriteArrayList<IDebugSessionListener> listeners = null;

    /** {@inheritDoc} */
    @Override
    public void addDebugSessionListener(IDebugSessionListener listener) {
        if (listener == null)
            return;
        if (this.listeners == null) {
            this.listeners = new CopyOnWriteArrayList<>();
        }
        this.listeners.add(listener);
    }

    /** {@inheritDoc} */
    @Override
    public void removeDebugSessionListener(IDebugSessionListener listener) {
        if (listener == null)
            return;
        if (this.listeners == null)
            return;
        this.listeners.remove(listener);
    }

    /**
     * Raises the {@link IDebugSessionListener#onThreadSuspended} event.
     *
     * @param thread The thread on which the event occurred.
     * @param cause The cause of the event.
     */
    protected void fireThreadSuspended(IAesiThread thread, SuspendEventCause cause) {
        if (this.listeners == null)
            return;
        SuspendThreadEvent event = new SuspendThreadEvent(this, thread, cause);
        for (IDebugSessionListener listener : this.listeners) {
            listener.onThreadSuspended(event);
        }
    }

    /**
     * Raises the {@link IDebugSessionListener#onThreadResumed} event.
     *
     * @param thread The thread on which the event occurred.
     * @param cause The cause of the event.
     */
    protected void fireThreadResumed(IAesiThread thread, SuspendEventCause cause) {
        if (this.listeners == null)
            return;
        SuspendThreadEvent event = new SuspendThreadEvent(this, thread, cause);
        for (IDebugSessionListener listener : this.listeners) {
            listener.onThreadResumed(event);
        }
    }

    /**
     * Raises the {@link IDebugSessionListener#onInitialized} event.
     */
    protected void fireInitialized() {
        if (this.listeners == null)
            return;
        EventObject event = new EventObject(this);
        for (IDebugSessionListener listener : this.listeners) {
            listener.onInitialized(event);
        }
    }

//    /**
//     * Raises the {@link IDebugSessionListener#onThreadInitialized} event.
//     *
//     * @param thread The thread on which the event occurred.
//     */
//    protected void fireThreadInitialized(IAesiThread thread) {
//        if (this.listeners == null)
//            return;
//        ThreadEvent event = new ThreadEvent(this, thread);
//        for (IDebugSessionListener listener : this.listeners) {
//            listener.onThreadInitialized(event);
//        }
//    }

    /**
     * Raises the {@link IDebugSessionListener#onThreadTerminated} event.
     *
     * @param thread The thread on which the event occurred.
     */
    protected void fireThreadTerminated(IAesiThread thread) {
        if (this.listeners == null)
            return;
        ThreadEvent event = new ThreadEvent(this, thread);
        for (IDebugSessionListener listener : this.listeners) {
            listener.onThreadTerminated(event);
        }
    }
}
