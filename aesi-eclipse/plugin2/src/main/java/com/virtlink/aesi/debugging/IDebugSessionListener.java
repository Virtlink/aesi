package com.virtlink.aesi.debugging;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Listens to debug session events.
 */
public interface IDebugSessionListener extends EventListener {

//    /**
//     * Event occurs when the debug session has been initialized
//     * and is ready to accept configuration events.
//     */
//    void initialized();
//
//    /**
//     * Event occurs when the debug session has been disconnected.
//     */
//    void disconnected();
//
//    /**
//     * Event occurs when a breakpoint is hit.
//     */
//    void breakpointHit();

    /**
     * Event occurs when the debugger has been initialized.
     *
     * @param event The event arguments.
     */
    void onInitialized(EventObject event);

    /**
     * Event occurs when execution of a thread has been suspended.
     *
     * @param event The event arguments.
     */
    void onThreadSuspended(SuspendThreadEvent event);

    /**
     * Event occurs when execution of a thread has been resumed.
     *
     * @param event The event arguments.
     */
    void onThreadResumed(SuspendThreadEvent event);

//    /**
//     * Event occurs when the thread has been initialized.
//     *
//     * @param event The event arguments.
//     */
//    void onThreadInitialized(ThreadEvent event);

    /**
     * Event occurs when the thread has been terminated.
     *
     * @param event The event arguments.
     */
    void onThreadTerminated(ThreadEvent event);

}
