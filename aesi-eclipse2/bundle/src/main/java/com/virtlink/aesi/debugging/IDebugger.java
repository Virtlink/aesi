package com.virtlink.aesi.debugging;

import java.util.List;

/**
 * A debugger.
 *
 * The debugger fires the {@link IDebuggerListener#onThreadCreated}
 * and {@link IDebuggerListener#onThreadDestroyed} events for any threads
 * that are created or destroyed after the debugger has been attached and
 * before the debugger has been detached.
 */
public interface IDebugger {

    /**
     * Initializes the debugger.
     *
     * This method is non-blocking.
     * When done, the {@link IDebuggerListener#onInitialized} event occurs.
     */
    void initialize();

    /**
     * Terminates the debugger.
     *
     * This method is non-blocking.
     * When done, the {@link IDebuggerListener#onTerminated} event occurs.
     */
    void terminate();

    /**
     * Attaches the debugger to a program.
     *
     * Depending on the arguments and the debugger's capabilities,
     * the program may be launched by the debugger
     * or may already be running.
     *
     * This method is non-blocking.
     * When done, the {@link IDebuggerListener#onAttached} event occurs.
     *
     * @param arguments The attach arguments.
     */
    void attach(IAttachOptions arguments);

    /**
     * Detaches the debugger from a program.
     *
     * Depending on the arguments and the debugger's capabilities,
     * the program may be terminated by the debugger
     * or may be kept running.
     *
     * This method is non-blocking.
     * When done, the {@link IDebuggerListener#onDetached} event occurs.
     *
     * @param arguments The detach arguments.
     */
    void detach(IDetachOptions arguments);

    /**
     * Suspends a single thread.
     *
     * This method is non-blocking.
     * When done, the {@link IDebuggerListener#onThreadSuspended} event occurs.
     */
    void suspend(IAesiThread thread);

    /**
     * Resumes a single thread.
     *
     * This method is non-blocking.
     * When done, the {@link IDebuggerListener#onThreadResumed} event occurs.
     */
    void resume(IAesiThread thread);

    /**
     * Performs a 'step over' in the specified thread.
     *
     * This method is non-blocking. It will fire the {@link IDebuggerListener#onThreadResumed}
     * and {@link IDebuggerListener#onThreadSuspended} events.
     */
    void step(IAesiThread thread);

    /**
     * Performs a 'step into' in the specified thread.
     *
     * This method is non-blocking. It will fire the {@link IDebuggerListener#onThreadResumed}
     * and {@link IDebuggerListener#onThreadSuspended} events.
     */
    void stepIn(IAesiThread thread);

    /**
     * Performs a 'step out of' in the specified thread.
     *
     * This method is non-blocking. It will fire the {@link IDebuggerListener#onThreadResumed}
     * and {@link IDebuggerListener#onThreadSuspended} events.
     */
    void stepOut(IAesiThread thread);

    /**
     * Gets the threads of the debuggee.
     *
     * Even when an implementation doesn't support (multiple) threads,
     * they must at least return one (dummy) thread. The first thread
     * is considered to be the main thread.
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
     * Adds a debugger listener.
     *
     * @param listener The listener to add.
     */
    void addListener(IDebuggerListener listener);

    /**
     * Removes a debugger listener.
     *
     * @param listener The listener to remove.
     */
    void removeListener(IDebuggerListener listener);

}
