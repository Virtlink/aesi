package com.virtlink.aesi.debugging;

import com.virtlink.aesi.eclipse.launching.AesiLaunchConfigurationDelegate;
import com.virtlink.aesi.eclipse.logging.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract base class for debuggers.
 *
 * The debugger must call the {@link #fireThreadCreated}
 * and {@link #fireThreadDestroyed} methods for any threads
 * that are created or destroyed after the debugger has been attached and
 * before the debugger has been detached. Then, for each thread created,
 * the debugger must call either the {@link #fireThreadResumed}
 * or the {@link #fireThreadSuspended} methods to indicate the running
 * state of the thread.
 */
public abstract class AbstractDebugger implements IDebugger {

    private static final Logger log = LoggerFactory.getLogger(AbstractDebugger.class);
    private DebuggerState state;
    @Nullable
    private CopyOnWriteArrayList<IDebuggerListener> listeners = null;
    @Nullable
    private ConcurrentHashMap<IAesiThread, ThreadState> threads = null;

    /**
     * Initializes a new instance of the {@link AbstractDebugger} class.
     */
    protected AbstractDebugger() {
        this.state = DebuggerState.Loaded;
    }

    /**
     * Gets the current state of the debugger.
     *
     * @return The current state of the debugger.
     */
    protected final DebuggerState getState() { return this.state; }

    /**
     * Gets whether the debugger can detach.
     * @return {@code true} when it can detach; otherwise, {@code false}.
     */
    public boolean canDetach() { return this.state == DebuggerState.Running; }


    /** {@inheritDoc} */
    public final void initialize() {
        log.debug("Initializing");
        assertState(DebuggerState.Loaded);
        doInitialize();
    }

    /**
     * Initializes the debugger.
     *
     * This method must be non-blocking.
     * When done, the {@link #fireInitialized} method must be called.
     */
    protected abstract void doInitialize();

    /** {@inheritDoc} */
    public final void terminate() {
        log.debug("Terminating");
        // The debugger can terminate
        // from any state after it's been initialized.
        assertState(DebuggerState.Ready, DebuggerState.Running);
        doTerminate();
    }

    /**
     * Terminates the debugger.
     *
     * This method must be non-blocking.
     * When done, the {@link #fireTerminated} method must be called.
     */
    protected abstract void doTerminate();

    /** {@inheritDoc} */
    public final void attach(IAttachOptions arguments) {
        log.debug("Attaching to " + arguments.getUri());
        assertState(DebuggerState.Ready);

        doAttach(arguments);
    }

    /**
     * Attaches the debugger to a program.
     *
     * Depending on the arguments and the debugger's capabilities,
     * the program may be launched by the debugger
     * or may already be running.
     *
     * This method must be non-blocking.
     * When done, the {@link #fireAttached} method must be called.
     *
     * @param arguments The attach arguments.
     */
    protected abstract void doAttach(IAttachOptions arguments);

    /** {@inheritDoc} */
    public final void detach(IDetachOptions arguments) {
        log.debug("Detaching (keepalive: " + arguments.keepAlive() + ")");

        assertState(DebuggerState.Running);
        doDetach(arguments);
    }

    /**
     * Detaches the debugger from a program.
     *
     * Depending on the arguments and the debugger's capabilities,
     * the program may be terminated by the debugger
     * or may be kept running.
     *
     * This method must be non-blocking.
     * When done, the {@link #fireDetached} method must be called.
     *
     * @param arguments The attach arguments.
     */
    protected abstract void doDetach(IDetachOptions arguments);

    /** {@inheritDoc} */
    public final void suspend(IAesiThread thread) {
        log.debug("Suspending " + thread.getName());
        assertState(DebuggerState.Running);
        doSuspend(thread);
    }

    /**
     * Suspends a single thread.
     *
     * This method must be non-blocking.
     * When done, the {@link #fireThreadSuspended} method must be called.
     */
    protected abstract void doSuspend(IAesiThread thread);

    /** {@inheritDoc} */
    public final void resume(IAesiThread thread) {
        log.debug("Resuming " + thread.getName());
        assertState(DebuggerState.Running);
        doResume(thread);
    }

    /**
     * Resumes a single thread.
     *
     * This method must be non-blocking.
     * When done, the {@link #fireThreadResumed} method must be called.
     */
    protected abstract void doResume(IAesiThread thread);

    /** {@inheritDoc} */
    public final void step(IAesiThread thread) {
        log.debug("Stepping " + thread.getName());
        assertState(DebuggerState.Running);
        doStep(thread);
    }

    /**
     * Performs a 'step over' in the specified thread.
     *
     * This method must be non-blocking. Call the {@link #fireThreadResumed}
     * and {@link #fireThreadSuspended} methods.
     */
    protected abstract void doStep(IAesiThread thread);

    /** {@inheritDoc} */
    public final void stepIn(IAesiThread thread) {
        log.debug("Stepping in " + thread.getName());
        assertState(DebuggerState.Running);
        doStepIn(thread);
    }

    /**
     * Performs a 'step into' in the specified thread.
     *
     * This method must be non-blocking. Call the {@link #fireThreadResumed}
     * and {@link #fireThreadSuspended} methods.
     */
    protected abstract void doStepIn(IAesiThread thread);

    /** {@inheritDoc} */
    public final void stepOut(IAesiThread thread) {
        log.debug("Stepping out " + thread.getName());
        assertState(DebuggerState.Running);
        doStepOut(thread);
    }

    /**
     * Performs a 'step out of' in the specified thread.
     *
     * This method must be non-blocking. Call the {@link #fireThreadResumed}
     * and {@link #fireThreadSuspended} methods.
     */
    protected abstract void doStepOut(IAesiThread thread);

    /**
     * Raises the {@link IDebuggerListener#onInitialized} event.
     */
    protected final void fireInitialized() {
        assertState(DebuggerState.Loaded);
        handleInitialized();

        if (this.listeners == null)
            return;
        EventObject event = new EventObject(this);
        for (IDebuggerListener listener : this.listeners) {
            listener.onInitialized(event);
        }
    }

    /**
     * Raises the {@link IDebuggerListener#onTerminated} event.
     */
    protected final void fireTerminated() {
        assertState(DebuggerState.Ready);
        handleTerminated();

        if (this.listeners == null)
            return;
        EventObject event = new EventObject(this);
        for (IDebuggerListener listener : this.listeners) {
            listener.onTerminated(event);
        }
    }

    /**
     * Raises the {@link IDebuggerListener#onAttached} event.
     *
     * This event is only allowed while the debugger
     * is in the {@link DebuggerState#Ready} state.
     */
    protected final void fireAttached() {
        assertState(DebuggerState.Ready);
        handleAttached();

        if (this.listeners == null)
            return;
        EventObject event = new EventObject(this);
        for (IDebuggerListener listener : this.listeners) {
            listener.onAttached(event);
        }
    }

    /**
     * Raises the {@link IDebuggerListener#onDetached} event.
     *
     * This event is only allowed while the debugger
     * is in the {@link DebuggerState#Running} state.
     */
    protected final void fireDetached() {
        assertState(DebuggerState.Running);
        handleDetached();

        if (this.listeners == null)
            return;
        EventObject event = new EventObject(this);
        for (IDebuggerListener listener : this.listeners) {
            listener.onDetached(event);
        }
    }

    /**
     * Raises the {@link IDebuggerListener#onThreadSuspended} event.
     *
     * This event is only allowed while the debugger
     * is in the {@link DebuggerState#Running} state.
     *
     * @param thread The thread that was suspended.
     * @param cause The reason the thread was suspended.
     */
    protected final void fireThreadSuspended(IAesiThread thread, SuspendEventCause cause) {
        assertState(DebuggerState.Running);
        handleThreadSuspended(thread, cause);

        if (this.listeners == null)
            return;
        SuspendThreadEvent event = new SuspendThreadEvent(this, thread, cause);
        for (IDebuggerListener listener : this.listeners) {
            listener.onThreadSuspended(event);
        }
    }

    /**
     * Raises the {@link IDebuggerListener#onThreadResumed} event.
     *
     * This event is only allowed while the debugger
     * is in the {@link DebuggerState#Running} state.
     *
     * @param thread The thread that was resumed.
     * @param cause The reason the thread was resumed.
     */
    protected final void fireThreadResumed(IAesiThread thread, SuspendEventCause cause) {
        assertState(DebuggerState.Running);
        handleThreadResumed(thread, cause);

        if (this.listeners == null)
            return;
        SuspendThreadEvent event = new SuspendThreadEvent(this, thread, cause);
        for (IDebuggerListener listener : this.listeners) {
            listener.onThreadResumed(event);
        }
    }

    /**
     * Raises the {@link IDebuggerListener#onThreadCreated} event.
     *
     * This event is only allowed while the debugger
     * is in the {@link DebuggerState#Running} state.
     *
     * @param thread The thread that was created.
     */
    protected final void fireThreadCreated(IAesiThread thread) {
        assertState(DebuggerState.Running);
        handleThreadCreated(thread);

        if (this.listeners == null)
            return;
        ThreadEvent event = new ThreadEvent(this, thread);
        for (IDebuggerListener listener : this.listeners) {
            listener.onThreadCreated(event);
        }
    }

    /**
     * Raises the {@link IDebuggerListener#onThreadDestroyed} event.
     *
     * This event is only allowed while the debugger
     * is in the {@link DebuggerState#Running} state.
     *
     * @param thread The thread that was destroyed.
     */
    protected final void fireThreadDestroyed(IAesiThread thread) {
        assertState(DebuggerState.Running);
        handleThreadDestroyed(thread);

        if (this.listeners == null)
            return;
        ThreadEvent event = new ThreadEvent(this, thread);
        for (IDebuggerListener listener : this.listeners) {
            listener.onThreadDestroyed(event);
        }
    }

    /**
     * Handles the `initialized` event.
     */
    protected /* virtual */ void handleInitialized() {
        this.state = DebuggerState.Ready;
        log.debug("Initialized");
    }

    /**
     * Handles the `terminated` event.
     */
    protected /* virtual */ void handleTerminated() {
        this.state = DebuggerState.Terminated;
        log.debug("Terminated");
    }

    /**
     * Handles the `attached` event.
     */
    protected /* virtual */ void handleAttached() {
        // We start tracking threads.
        this.threads = new ConcurrentHashMap<>();

        List<IAesiThread> initialThreads = doGetThreads();
        for (IAesiThread thread : initialThreads) {
            this.threads.put(thread, ThreadState.Initialized);
        }

        this.state = DebuggerState.Running;
        log.debug("Attached");
    }

    /**
     * Handles the `detached` event.
     */
    protected /* virtual */ void handleDetached() {
        // We're done tracking threads.
        this.threads = null;

        this.state = DebuggerState.Ready;
        log.debug("Detached");
    }

    /**
     * Handles the `thread suspended` event.
     *
     * @param thread The thread that was suspended.
     * @param cause The cause of the suspension.
     */
    protected /* virtual */ void handleThreadSuspended(IAesiThread thread, SuspendEventCause cause) {
        assert this.threads != null;

        ThreadState state = this.threads.getOrDefault(thread, null);
        if (state == null)
            throw new IllegalStateException("The thread is not known: " + thread.getName());

        switch (state) {
            case Initialized:
            case Running:
                this.threads.put(thread, ThreadState.Suspended);
                break;
            case Suspended:
                log.debug("The thread is already suspended: " + thread.getName());
                break;
            case Terminated:
                throw new IllegalStateException("The thread has been terminated: " + thread.getName());
        }

        log.debug("Suspended " + thread.getName() + " for " + cause);
    }

    /**
     * Handles the `thread resumed` event.
     *
     * @param thread The thread that was resumed.
     * @param cause The cause of the resumption.
     */
    protected /* virtual */ void handleThreadResumed(IAesiThread thread, SuspendEventCause cause) {
        assert this.threads != null;

        ThreadState state = this.threads.getOrDefault(thread, null);
        if (state == null)
            throw new IllegalStateException("The thread is not known: " + thread.getName());

        switch (state) {
            case Initialized:
            case Suspended:
                this.threads.put(thread, ThreadState.Running);
                break;
            case Running:
                log.debug("The thread is already running: " + thread.getName());
                break;
            case Terminated:
                throw new IllegalStateException("The thread has been terminated: " + thread.getName());
        }

        log.debug("Resumed " + thread.getName() + " for " + cause);
    }

    /**
     * Handles the `thread created` event.
     *
     * @param thread The thread that was created.
     */
    protected /* virtual */ void handleThreadCreated(IAesiThread thread) {
        if (this.threads == null) {
            // We aren't tracking threads.
            return;
        }

        this.threads.put(thread, ThreadState.Initialized);

        log.debug("Created " + thread.getName());
    }

    /**
     * Handles the `thread destroyed` event.
     *
     * @param thread The thread that was destroyed.
     */
    protected /* virtual */ void handleThreadDestroyed(IAesiThread thread) {
        if (this.threads == null) {
            // We aren't tracking threads.
            return;
        }

        this.threads.remove(thread);

        log.debug("Destroyed " + thread.getName());
    }

    /** {@inheritDoc} */
    @Override
    public final List<IAesiThread> getThreads() {
        if (this.threads != null) {
            return Collections.list(this.threads.keys());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Returns all threads that exist when the debugger is attached.
     *
     * The implementation uses this to get an initial list of threads
     * in the program. When threads are created or destroyed while
     * the program is running should call the appropriate {@link #fireThreadCreated}
     * and {@link #fireThreadDestroyed} methods. This implementation will
     * use those events to track the current list of threads and their states.
     *
     * @return A list of threads.
     */
    protected abstract List<IAesiThread> doGetThreads();

    /** {@inheritDoc} */
    @Override
    public final void addListener(IDebuggerListener listener) {
        if (listener == null)
            return;
        if (this.listeners == null) {
            this.listeners = new CopyOnWriteArrayList<>();
        }
        this.listeners.add(listener);
    }

    /** {@inheritDoc} */
    @Override
    public final void removeListener(IDebuggerListener listener) {
        if (listener == null)
            return;
        if (this.listeners == null)
            return;
        this.listeners.remove(listener);
    }

    /**
     * Asserts that the debugger is in the expected state.
     * @param expectedStates The expected states.
     * @throws IllegalStateException The current state does not match the expected state.
     */
    protected void assertState(DebuggerState... expectedStates) {
        DebuggerState currentState = getState();
        if (!Arrays.asList(expectedStates).contains(currentState)) {
            throw new IllegalStateException("This action is only allowed in the "
                    + Stream.of(expectedStates).map(Object::toString).collect(Collectors.joining(", "))
                    + " state(s), the debugger is currently in the " + currentState + " state.");
        }
    }
}
