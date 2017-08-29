package com.virtlink.aesi.debugging;

/**
 * Specifies what caused a suspend/resume event.
 */
public enum SuspendEventCause {
    /**
     * None.
     */
    None,
    /**
     * Request to suspend/resume execution.
     */
    Client,
    /**
     * Hit a breakpoint.
     */
    Breakpoint,
    /**
     * Hit an exception breakpoint.
     */
    Exception,
    /**
     * Request to perform a step into a scope.
     */
    StepIn,
    /**
     * Request to perform a step.
     */
    Step,
    /**
     * Request to perform a step out of a scope.
     */
    StepOut,
//    /**
//     * Request to perform a step back.
//     */
//    StepBack,
}
