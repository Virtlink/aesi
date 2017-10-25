package com.virtlink.aesi.debugging;

import java.util.EventObject;

public interface IDebuggerListener {

    /**
     * Event occurs when the debugger has been initialized.
     *
     * This is guaranteed to be the first event raised by the debugger.
     *
     * @param event The event arguments.
     */
    default void onInitialized(EventObject event) {}

    /**
     * Event occurs when the debugger has been terminated.
     *
     * This is guaranteed to be the last event raised by the debugger.
     *
     * @param event The event arguments.
     */
    default void onTerminated(EventObject event) {}

    /**
     * Event occurs when the debugger has been attached to
     * a launched or running program.
     *
     * This event only occurs while the debugger is unattached.
     *
     * @param event The event arguments.
     */
    default void onAttached(EventObject event) {}

    /**
     * Event occurs when the debugger has been detached from
     * a program (or the program was destroyed).
     *
     * This event only occurs while the debugger is attached.
     *
     * @param event The event arguments.
     */
    default void onDetached(EventObject event) {}

    /**
     * Event occurs when a thread has been created.
     *
     * This event only occurs while the debugger is attached.
     *
     * @param event The event arguments.
     */
    default void onThreadCreated(ThreadEvent event) {}

    /**
     * Event occurs when a thread has been destroyed.
     *
     * This event only occurs while the debugger is attached.
     *
     * @param event The event arguments.
     */
    default void onThreadDestroyed(ThreadEvent event) {}

    /**
     * Event occurs when a thread has resumed execution.
     *
     * This event only occurs while the debugger is attached.
     *
     * @param event The event arguments.
     */
    default void onThreadResumed(SuspendThreadEvent event) {}

    /**
     * Event occurs when a thread has suspended execution.
     *
     * This event only occurs while the debugger is attached.
     *
     * @param event The event arguments.
     */
    default void onThreadSuspended(SuspendThreadEvent event) {}
}
