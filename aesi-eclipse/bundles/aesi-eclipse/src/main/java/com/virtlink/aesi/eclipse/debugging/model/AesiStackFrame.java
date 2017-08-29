package com.virtlink.aesi.eclipse.debugging.model;

import com.virtlink.aesi.debugging.IAesiStackFrame;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

/**
 * A stack frame.
 */
public class AesiStackFrame extends AbstractStackFrame {

    private final IAesiStackFrame stackFrame;

    /**
     * Initializes a new instance of the {@link AbstractStackFrame} class.
     *
     * @param thread The thread to which this stack frame belongs.
     * @param stackFrame The corresponding AESI stack frame.
     */
    public AesiStackFrame(IThread thread, IAesiStackFrame stackFrame) {
        super(thread);

        this.stackFrame = stackFrame;
    }

    @Override
    public String getName() throws DebugException {
        return this.stackFrame.getName();
    }

    @Override
    public int getLineNumber() throws DebugException {
        return 0;
    }

    @Override
    public int getCharStart() throws DebugException {
        return 0;
    }

    @Override
    public int getCharEnd() throws DebugException {
        return 0;
    }

    @Override
    public IVariable[] getVariables() throws DebugException {
        return new IVariable[0];
    }
}
