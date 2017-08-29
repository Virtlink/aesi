package com.virtlink.aesi.eclipse.debugging.model;

import com.virtlink.aesi.debugging.*;
import com.virtlink.aesi.eclipse.AesiPlugin;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.*;
import org.eclipse.debug.core.model.IProcess;

import javax.annotation.Nullable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;

/**
 * The debug target communicates with the debugger implementation.
 */
public class AesiDebugTarget extends AesiDebugElement implements IDebugTarget, IDebuggerListener {

    private DebuggerState state;
    private final ILaunch launch;
    private final AbstractDebugger debugger;
    @Nullable
    private IAesiThread mainThread = null;
    private final HashMap<IAesiThread, AesiDebugThread> threads = new HashMap<>();

    /**
     * Gets the debugger.
     *
     * @return The debugger.
     */
    public IDebugger getDebugger() { return this.debugger; }

    /**
     * Gets the main Eclipse thread.
     * @return The main Eclipse thread, or {@code null}.
     */
    @Nullable
    protected IThread getMainEclipseThread() {
        if (this.mainThread == null)
            return null;
        return this.threads.get(this.mainThread);
    }

    /**
     * Initializes a new instance of the {@link AesiDebugTarget} class.
     *
     * @param launch The launched debug session.
     * @param debugger The debugger.
     */
    public AesiDebugTarget(ILaunch launch, AbstractDebugger debugger) {
        //noinspection ConstantConditions
        super(null);

        this.state = DebuggerState.Ready;
        this.launch = launch;
        this.debugger = debugger;
        this.debugger.addListener(this);
        this.debugger.initialize();
    }

    /** {@inheritDoc} */
    @Override
    public IDebugTarget getDebugTarget() { return this; }

    /** {@inheritDoc} */
    @Override
    public ILaunch getLaunch() { return this.launch; }

    /** {@inheritDoc} */
    @Override
    public String getName() throws DebugException {
        return "AESI";
    }

    /** {@inheritDoc} */
    @Override
    public boolean supportsStorageRetrieval() {
        // Not supported.
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
        throw new DebugException(new Status(Status.ERROR, AesiPlugin.PLUGIN_ID,
                "Not supported."));
    }

    /** {@inheritDoc} */
    @SuppressWarnings("ConstantConditions")
    @Override
    public IProcess getProcess() {
        // We can return null here.
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public IThread[] getThreads() throws DebugException {
        return this.threads.values().toArray(new IThread[0]);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasThreads() throws DebugException {
        IThread[] threads = getThreads();
        return threads.length > 0;
    }

    @Override
    public boolean supportsBreakpoint(IBreakpoint breakpoint) {
//        breakpoint.getMarker().getResource().
        // TODO
        return false;
    }

    @Override
    public void breakpointAdded(IBreakpoint breakpoint) {
        if (!supportsBreakpoint(breakpoint))
            return;
        AesiLineBreakpoint aesiBreakpoint = (AesiLineBreakpoint)breakpoint;
        if (aesiBreakpoint.isEnabled()) {
            // TODO
        }
    }

    @Override
    public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
// TODO
    }

    @Override
    public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
// TODO
    }

    private void installDeferredBreakpoints() {
        IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager()
                .getBreakpoints(AesiPlugin.getDebugModelIdentifier());
        for (final IBreakpoint breakpoint : breakpoints) {
            breakpointAdded(breakpoint);
        }
    }

    /** {@inheritDoc } */
    @Override
    public boolean canResume() {
        @Nullable IThread thread = getMainEclipseThread();
        return thread != null && thread.canResume();
    }

    /** {@inheritDoc } */
    @Override
    public void resume() throws DebugException {
        @Nullable IThread thread = getMainEclipseThread();
        if (thread != null)
            thread.resume();
    }

    /** {@inheritDoc } */
    @Override
    public boolean canSuspend() {
        @Nullable IThread thread = getMainEclipseThread();
        return thread != null && thread.canSuspend();
    }

    /** {@inheritDoc } */
    @Override
    public void suspend() throws DebugException {
        @Nullable IThread thread = getMainEclipseThread();
        if (thread != null)
            thread.suspend();
    }

    /** {@inheritDoc } */
    @Override
    public boolean isSuspended() {
        @Nullable IThread thread = getMainEclipseThread();
        return thread != null && thread.isSuspended();
    }

    /** {@inheritDoc } */
    @Override
    public boolean canDisconnect() {
        // Only if attached/running.
        return this.state == DebuggerState.Running;
    }

    /** {@inheritDoc } */
    @Override
    public void disconnect() throws DebugException {
        this.debugger.detach(new IDetachOptions() {
            @Override
            public boolean keepAlive() { return false; }
        });
    }

    /** {@inheritDoc } */
    @Override
    public boolean isDisconnected() {
        return this.state == DebuggerState.Ready;
    }

    /** {@inheritDoc } */
    @Override
    public boolean canTerminate() {
        return !isTerminated();
    }

    /** {@inheritDoc } */
    @Override
    public void terminate() throws DebugException {
        if (this.debugger.canDetach()) {
            this.debugger.detach(new IDetachOptions() {
                @Override
                public boolean keepAlive() {
                    return false;
                }
            });
        }
        this.debugger.terminate();
    }

    /** {@inheritDoc } */
    @Override
    public boolean isTerminated() {
        return this.state == DebuggerState.Terminated;
    }

    /** {@inheritDoc } */
    @Override
    public void onInitialized(EventObject event) {
        fireCreationEvent();
        this.state = DebuggerState.Ready;
        this.debugger.attach(new IAttachOptions() {

            @Override
            public URI getUri() {
                try {
                    return new URI("test:program");
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public boolean shouldSuspend() {
                return true;
            }
        });
    }

    /** {@inheritDoc } */
    @Override
    public void onTerminated(EventObject event) {
        this.state = DebuggerState.Terminated;
    }

    /** {@inheritDoc } */
    @Override
    public void onAttached(EventObject event) {
        this.state = DebuggerState.Running;

        List<IAesiThread> threads = this.debugger.getThreads();
        for (IAesiThread thread : threads) {
            addThread(thread);
        }

        installDeferredBreakpoints();
    }

    /** {@inheritDoc } */
    @Override
    public void onDetached(EventObject event) {
        this.state = DebuggerState.Ready;

        for (IAesiThread thread : this.threads.keySet()) {
            removeThread(thread);
        }
    }

    /** {@inheritDoc } */
    @Override
    public void onThreadCreated(ThreadEvent event) {
        addThread(event.getThread());
    }

    /** {@inheritDoc } */
    @Override
    public void onThreadDestroyed(ThreadEvent event) {
        removeThread(event.getThread());
    }

    /**
     * Adds a thread.
     * @param thread The thread to add.
     */
    private void addThread(IAesiThread thread) {
        AesiDebugThread debugThread = new AesiDebugThread(this, thread);
        this.threads.put(thread, debugThread);
    }

    /**
     * Removes a thread.
     * @param thread The thread to remove.
     */
    private void removeThread(IAesiThread thread) {
        AesiDebugThread debugThread = this.threads.remove(thread);
        debugThread.dispose();
    }
}
