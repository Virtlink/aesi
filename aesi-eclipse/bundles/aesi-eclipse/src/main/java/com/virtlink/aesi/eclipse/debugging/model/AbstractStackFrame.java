package com.virtlink.aesi.eclipse.debugging.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.*;

public abstract class AbstractStackFrame extends DebugElement implements IStackFrame {

    private final IThread thread;

    /** {@inheritDoc} */
    @Override
    public IThread getThread() {
        return this.thread;
    }

    /**
     * Initializes a new instance of the {@link AbstractStackFrame} class.
     *
     * @param thread The thread to which this stack frame belongs.
     */
    public AbstractStackFrame(IThread thread) {
        super(thread.getDebugTarget());

        this.thread = thread;
    }

    /** {@inheritDoc} */
    @Override
    public abstract String getName() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract int getLineNumber() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract int getCharStart() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract int getCharEnd() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public abstract IVariable[] getVariables() throws DebugException;

    /** {@inheritDoc} */
    @Override
    public boolean hasVariables() throws DebugException {
        return getVariables().length > 0;
    }

    /** {@inheritDoc} */
    @Override
    public IRegisterGroup[] getRegisterGroups() throws DebugException {
        return new IRegisterGroup[0];
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRegisterGroups() throws DebugException {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String getModelIdentifier() {
        return this.thread.getModelIdentifier();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canStepInto() {
        return this.thread.canStepInto();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canStepOver() {
        return this.thread.canStepOver();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canStepReturn() {
        return this.thread.canStepReturn();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canResume() {
        return this.thread.canResume();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canSuspend() {
        return this.thread.canSuspend();
    }

    /** {@inheritDoc} */
    @Override
    public boolean canTerminate() {
        return this.thread.canTerminate();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isStepping() {
        return this.thread.isStepping();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isSuspended() {
        return this.thread.isSuspended();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isTerminated() {
        return this.thread.isTerminated();
    }

    /** {@inheritDoc} */
    @Override
    public void stepInto() throws DebugException {
        this.thread.stepInto();
    }

    /** {@inheritDoc} */
    @Override
    public void stepOver() throws DebugException {
        this.thread.stepOver();
    }

    /** {@inheritDoc} */
    @Override
    public void stepReturn() throws DebugException {
        this.thread.stepReturn();
    }

    /** {@inheritDoc} */
    @Override
    public void resume() throws DebugException {
        this.thread.resume();
    }

    /** {@inheritDoc} */
    @Override
    public void suspend() throws DebugException {
        this.thread.suspend();
    }

    /** {@inheritDoc} */
    @Override
    public void terminate() throws DebugException {
        this.thread.terminate();
    }
}
