package com.virtlink.aesi.debugging;


import java.util.List;

/**
 * A debug session.
 */
public interface IDebugSession {

    /**
     * Adds a debug session listener.
     *
     * @param listener The listener to add.
     */
    void addDebugSessionListener(IDebugSessionListener listener);

    /**
     * Removes a debug session listener.
     *
     * @param listener The listener to remove.
     */
    void removeDebugSessionListener(IDebugSessionListener listener);

    /**
     * Gets the threads of the debuggee.
     *
     * Even when an implementation doesn't support (multiple) threads,
     * they must at least return one (dummy) thread.
     *
     * @return A list of threads.
     */
    List<IAesiThread> getThreads();

    /**
     * Gets the stack frames for the specified thread.
     *
     * Even when an implementation doesn't support stack frames,
     * they must at least return one (dummy) stack frame.
     *
     * This method is only called while execution is suspended.
     *
     * @param thread The thread.
     * @return A list of stack frames.
     */
    List<IAesiStackFrame> getStackFrames(IAesiThread thread);

    /**
     * Gets the scopes for the specified stack frame.
     *
     * Even when an implementation doesn't support scopes,
     * they must at least return one (dummy) scope.
     *
     * This method is only called while execution is suspended.
     *
     * @param thread The thread.
     * @param stackFrame The stack frame.
     * @return A list of stack frames.
     */
    List<IScope> getScopes(IAesiThread thread, IAesiStackFrame stackFrame);

    /**
     * Gets the variables for the specified scope.
     *
     * This method is only called while execution is suspended.
     *
     * @param thread The thread.
     * @param stackFrame The stack frame.
     * @param scope The scope.
     * @return A list of variables.
     */
    List<IVariable> getVariables(IAesiThread thread, IAesiStackFrame stackFrame, IScope scope);

    /**
     * Gets the child variables for the variable.
     *
     * This method is only called while execution is suspended.
     *
     * @param thread The thread.
     * @param stackFrame The stack frame.
     * @param scope The scope.
     * @param variable The variable.
     * @return A list of variables.
     */
    List<IVariable> getChildVariables(IAesiThread thread, IAesiStackFrame stackFrame, IScope scope, IVariable variable);

    /**
     * Initialize the session.
     *
     * This method must be non-blocking. When initialization has been completed,
     * the {@link IDebugSessionListener#onThreadInitialized} event must be fired.
     */
    void initialize();

    /**
     * Suspend execution.
     *
     * This method must be non-blocking. When execution has been suspended,
     * the {@link IDebugSessionListener#onThreadSuspended} event must be fired.
     *
     * @param thread The thread to suspend.
     */
    void suspend(IAesiThread thread);

    /**
     * Resume execution.
     *
     * This method must be non-blocking. When execution has been resumed,
     * the {@link IDebugSessionListener#onThreadResumed} event must be fired.
     *
     * @param thread The thread to resume.
     */
    void resume(IAesiThread thread);

//    /**
//     * Resume backward execution.
//     *
//     * This method must be non-blocking. When execution has been resumed,
//     * the {@link IDebugSessionListener#onThreadResumed} event must be fired.
//     *
//     * @param thread The thread to resume.
//     */
//    void resumeBackward(IAesiThread thread);

    /**
     * Terminates execution.
     *
     * This method must be non-blocking. When execution has been resumed,
     * the {@link IDebugSessionListener#onThreadTerminated} event must be fired.
     *
     * @param thread The thread to terminate.
     */
    void terminate(IAesiThread thread);

    /**
     * Step into the child scope.
     *
     * This method must be non-blocking. When execution has been resumed,
     * the {@link IDebugSessionListener#onThreadResumed} event must be fired.
     * When execution then arrives at the desired position and is suspended,
     * the {@link IDebugSessionListener#onThreadSuspended} event must be fired.
     *
     * @param thread The thread to step.
     */
    void stepIn(IAesiThread thread);

    /**
     * Step out of the current scope.
     *
     * This method must be non-blocking. When execution has been resumed,
     * the {@link IDebugSessionListener#onThreadResumed} event must be fired.
     * When execution then arrives at the desired position and is suspended,
     * the {@link IDebugSessionListener#onThreadSuspended} event must be fired.
     *
     * @param thread The thread to step.
     */
    void stepOut(IAesiThread thread);

    /**
     * Step forward.
     *
     * This method must be non-blocking. When execution has been resumed,
     * the {@link IDebugSessionListener#onThreadResumed} event must be fired.
     * When execution then arrives at the desired position and is suspended,
     * the {@link IDebugSessionListener#onThreadSuspended} event must be fired.
     *
     * @param thread The thread to step.
     */
    void step(IAesiThread thread);

//    /**
//     * Step backward.
//     *
//     * This method must be non-blocking. When execution has been resumed,
//     * the {@link IDebugSessionListener#onThreadResumed} event must be fired.
//     * When execution then arrives at the desired position and is suspended,
//     * the {@link IDebugSessionListener#onThreadSuspended} event must be fired.
//     *
//     * @param thread The thread to step.
//     */
//    void stepBack(IAesiThread thread);

    // TODO: Requires an argument that specifies where to go to.
    // NOTE: Can jump anywhere, including backward, but doesn't undo changes like stepBack().
    /**
     * Changes the current execution point.
     *
     * @param thread The thread.
     */
    void skipTo(IAesiThread thread);
}
