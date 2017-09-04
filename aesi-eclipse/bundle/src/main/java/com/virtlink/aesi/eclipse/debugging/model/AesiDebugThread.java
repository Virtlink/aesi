package com.virtlink.aesi.eclipse.debugging.model;

import com.virtlink.aesi.debugging.*;
import com.virtlink.aesi.eclipse.AesiPlugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.*;

public class AesiDebugThread extends AbstractDebugThread implements IDebuggerListener {

    private final IAesiThread thread;
    private State state;

    /**
     * Used for locking.
     */
    private final Object lock = new Object();

    private enum State {
        Initializing,
        Suspended,
        Stepping,
        Running,
        Terminating,
        Terminated,
    }

    /**
     * {@inheritDoc}
     */
    public AesiDebugTarget getDebugTarget() {
        return (AesiDebugTarget)super.getDebugTarget();
    }

    /**
     * Gets the debugger.
     *
     * @return The debugger.
     */
    private IDebugger getDebugger() {
        return getDebugTarget().getDebugger();
    }

    /**
     * Initializes a new instance of the {@link AesiDebugThread} class.
     *
     * @param target The debug target to which this thread belongs.
     * @param thread The AESI thread.
     */
    public AesiDebugThread(IDebugTarget target, IAesiThread thread) {
        super(target);

        this.thread = thread;
        this.state = State.Initializing;
        getDebugger().addListener(this);
    }

    /**
     * Disposes this object.
     */
    public void dispose() {
        getDebugger().removeListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() throws DebugException {
        return getStatusPrefix() + this.thread.getName();
    }

    /**
     * Returns a prefix for the name of the thread.
     *
     * @return The prefix.
     */
    private String getStatusPrefix() throws DebugException {
        if (isSuspended()) return "<suspended> ";
        else if (!isTerminated()) return "<running> ";
        else return "";
    }

    /** {@inheritDoc} */
    @Override
    public IStackFrame[] getStackFrames() throws DebugException {
        // TODO
        return new IStackFrame[0];
    }

    /** {@inheritDoc} */
    @Override
    public IBreakpoint[] getBreakpoints() {
        // TODO
        return new IBreakpoint[0];
    }

    /** {@inheritDoc} */
    @Override
    public boolean isStepping() {
        synchronized (this.lock) {
            return this.state == State.Stepping;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isSuspended() {
        synchronized (this.lock) {
            return this.state == State.Suspended;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isTerminated() {
        synchronized (this.lock) {
            return this.state == State.Terminated;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void stepInto() throws DebugException {
        synchronized (this.lock) {
            assertSuspended();

            getDebugger().stepIn(this.thread);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void stepOver() throws DebugException {
        synchronized (this.lock) {
            assertSuspended();

            getDebugger().step(this.thread);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void stepReturn() throws DebugException {
        synchronized (this.lock) {
            assertSuspended();

            getDebugger().stepOut(this.thread);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void resume() throws DebugException {
        synchronized (this.lock) {
            assertSuspended();

            getDebugger().resume(this.thread);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void suspend() throws DebugException {
        synchronized (this.lock) {
            assertRunning();

            getDebugger().suspend(this.thread);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean canTerminate() {
        // Not supported.
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void terminate() throws DebugException {
        throw new DebugException(new Status(Status.ERROR, AesiPlugin.PLUGIN_ID,
                "Not supported."));
    }

    /** {@inheritDoc} */
    @Override
    public void onThreadDestroyed(ThreadEvent event) {
        if (event.getThread() != this.thread)
            return;

        synchronized (this.lock) {
            this.state = State.Terminated;
            fireTerminateEvent();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onThreadResumed(SuspendThreadEvent event) {
        if (event.getThread() != this.thread)
            return;

        synchronized (this.lock) {
            switch (event.getCause()) {
                case Client:
                    this.state = State.Running;
                    fireResumeEvent(DebugEvent.UNSPECIFIED);
                    break;
                case StepIn:
                    this.state = State.Stepping;
                    fireResumeEvent(DebugEvent.STEP_INTO);
                    break;
                case Step:
                    this.state = State.Stepping;
                    fireResumeEvent(DebugEvent.STEP_OVER);
                    break;
                case StepOut:
                    this.state = State.Stepping;
                    fireResumeEvent(DebugEvent.STEP_RETURN);
                    break;
                default:
                    assert false : "Unsupported or unrecognized.";
                    return;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onThreadSuspended(SuspendThreadEvent event) {
        if (event.getThread() != this.thread)
            return;

        synchronized (this.lock) {
            this.state = State.Suspended;

            switch (event.getCause()) {
                case Client:
                    fireSuspendEvent(DebugEvent.SUSPEND);
                    break;
                case StepIn:
                case Step:
                case StepOut:
                    fireSuspendEvent(DebugEvent.STEP_END);
                    break;
                case Breakpoint:
                case Exception:
                    fireSuspendEvent(DebugEvent.BREAKPOINT);
                    break;
                default:
                    assert false : "Unsupported or unrecognized.";
                    return;
            }
        }
    }

    /**
     * Asserts that the thread is suspended.
     *
     * @throws DebugException The thread is not suspended.
     */
    private void assertSuspended() throws DebugException {
        if (!this.isSuspended()) {
            throw new DebugException(new Status(Status.ERROR,
                    AesiPlugin.PLUGIN_ID,
                    Status.OK,
                    "Action is only supported while the thread is suspended.",
                    null));
        }
    }

    /**
     * Asserts that the thread is suspended.
     *
     * @throws DebugException The thread is not suspended.
     */
    private void assertRunning() throws DebugException {
        if (this.isSuspended()) {
            throw new DebugException(new Status(Status.ERROR,
                    AesiPlugin.PLUGIN_ID,
                    Status.OK,
                    "Action is only supported while the thread is running.",
                    null));
        }
    }
}
