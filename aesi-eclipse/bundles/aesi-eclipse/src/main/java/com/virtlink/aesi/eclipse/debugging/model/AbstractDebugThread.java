package com.virtlink.aesi.eclipse.debugging.model;

import com.virtlink.aesi.debugging.IAesiThread;
import com.virtlink.aesi.eclipse.AesiPlugin;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.*;

import javax.annotation.Nullable;

/**
 * Abstract base class for debug thread implementations.
 */
public abstract class AbstractDebugThread extends DebugElement implements IThread {

    /**
     * Initializes a new instance of the {@link AbstractDebugThread} class.
     *
     * @param target The debug target to which this thread belongs.
     */
    protected AbstractDebugThread(IDebugTarget target) {
        super(target);
    }

    /** {@inheritDoc} */
    @Override
    public abstract String getName() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract IStackFrame[] getStackFrames() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract IBreakpoint[] getBreakpoints();

    /** {@inheritDoc} */
    @Override
    public abstract boolean isStepping();

    /** {@inheritDoc} */
    @Override
    public abstract boolean isSuspended();

    /** {@inheritDoc} */
    @Override
    public abstract boolean isTerminated();

    /** {@inheritDoc} */
    @Override
    public abstract void stepInto() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract void stepOver() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract void stepReturn() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract void resume() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract void suspend() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract void terminate() throws DebugException;




    /** {@inheritDoc} */
    @Override
    public String getModelIdentifier() {
        return AesiPlugin.getDebugModelIdentifier();
    }

    /** {@inheritDoc} */
    @Override
    @Nullable
    public IStackFrame getTopStackFrame() throws DebugException {
        IStackFrame[] stackFrames = getStackFrames();
        return stackFrames.length > 0 ? stackFrames[0] : null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasStackFrames() throws DebugException {
        return getStackFrames().length > 0;
    }

    /** {@inheritDoc} */
    @Override
    public int getPriority() throws DebugException {
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean canResume() {
        return isSuspended();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canSuspend() { return true; }

    /** {@inheritDoc} */
    @Override
    public boolean canTerminate() {
        return !isTerminated();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canStepInto() {
        return isSuspended();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canStepOver() {
        return isSuspended();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canStepReturn() {
        return isSuspended();
    }

}
