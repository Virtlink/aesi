package com.virtlink.aesi.debugging;

import java.util.concurrent.CompletableFuture;

/**
 * Abstract base class for asynchronous debuggers.
 */
public abstract class AbstractAsyncDebugger extends AbstractDebugger {

    /**
     * Initializes a new instance of the {@link AbstractAsyncDebugger} class.
     */
    protected AbstractAsyncDebugger() {
        super();
    }

    /**
     * Asynchronously performs the `initialize` action.
     *
     * @return The future.
     */
    protected abstract CompletableFuture<Void> initializeAsync();

    /**
     * Asynchronously performs the `terminate` action.
     *
     * @return The future.
     */
    protected abstract CompletableFuture<Void> terminateAsync();

    /**
     * Asynchronously performs the `attach` action.
     *
     * @param arguments The attach arguments.
     * @return The future.
     */
    protected abstract CompletableFuture<Void> attachAsync(IAttachOptions arguments);

    /**
     * Asynchronously performs the `detach` action.
     *
     * @param arguments The detach arguments.
     * @return The future.
     */
    protected abstract CompletableFuture<Void> detachAsync(IDetachOptions arguments);

    /**
     * Asynchronously performs the `suspend` action on the specified thread.
     *
     * @param thread The thread.
     * @return The future.
     */
    protected abstract CompletableFuture<Void> suspendAsync(IAesiThread thread);

    /**
     * Asynchronously performs the `resume` action on the specified thread.
     *
     * @param thread The thread.
     * @return The future.
     */
    protected abstract CompletableFuture<Void> resumeAsync(IAesiThread thread);

    /**
     * Asynchronously performs the `step` action on the specified thread.
     *
     * @param thread The thread.
     * @return The future.
     */
    protected abstract CompletableFuture<Void> stepAsync(IAesiThread thread);

    /**
     * Asynchronously performs the `step in` action on the specified thread.
     *
     * @param thread The thread.
     * @return The future.
     */
    protected abstract CompletableFuture<Void> stepInAsync(IAesiThread thread);

    /**
     * Asynchronously performs the `step out` action on the specified thread.
     *
     * @param thread The thread.
     * @return The future.
     */
    protected abstract CompletableFuture<Void> stepOutAsync(IAesiThread thread);

    /** {@inheritDoc} */
    @Override
    protected final void doInitialize() {
        initializeAsync()
                .thenRun(this::fireInitialized);
    }

    /** {@inheritDoc} */
    @Override
    protected final void doTerminate() {
        terminateAsync()
                .thenRun(this::fireTerminated);
    }

    /** {@inheritDoc} */
    @Override
    protected final void doAttach(IAttachOptions arguments) {
        attachAsync(arguments)
                .thenRun(this::fireAttached);
    }

    /** {@inheritDoc} */
    @Override
    protected final void doDetach(IDetachOptions arguments) {
        detachAsync(arguments)
                .thenRun(this::fireDetached);
    }

    /** {@inheritDoc} */
    @Override
    protected final void doSuspend(IAesiThread thread) {
        suspendAsync(thread)
                .thenRun(() -> fireThreadSuspended(thread, SuspendEventCause.Client));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doResume(IAesiThread thread) {
        resumeAsync(thread)
                .thenRun(() -> fireThreadResumed(thread, SuspendEventCause.Client));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doStep(IAesiThread thread) {
        CompletableFuture
                .runAsync(() -> fireThreadResumed(thread, SuspendEventCause.Step))
                .thenCompose(x -> stepAsync(thread))
                .thenRun(() -> fireThreadSuspended(thread, SuspendEventCause.Step));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doStepIn(IAesiThread thread) {
        CompletableFuture
                .runAsync(() -> fireThreadResumed(thread, SuspendEventCause.StepIn))
                .thenCompose(x -> stepInAsync(thread))
                .thenRun(() -> fireThreadSuspended(thread, SuspendEventCause.StepIn));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doStepOut(IAesiThread thread) {
        CompletableFuture
                .runAsync(() -> fireThreadResumed(thread, SuspendEventCause.StepOut))
                .thenCompose(x -> stepOutAsync(thread))
                .thenRun(() -> fireThreadSuspended(thread, SuspendEventCause.StepOut));
    }

//    /** {@inheritDoc} */
//    @Override
//    protected abstract List<IAesiThread> doGetThreads();
//
//    /** {@inheritDoc} */
//    @Override
//    public abstract List<IAesiStackFrame> getStackFrames(IAesiThread thread);
//
//    /** {@inheritDoc} */
//    @Override
//    public abstract List<IScope> getScopes(IAesiThread thread, IAesiStackFrame stackFrame);
//
//    /** {@inheritDoc} */
//    @Override
//    public abstract List<IVariable> getVariables(IAesiThread thread, IAesiStackFrame stackFrame, IScope scope);
//
//    /** {@inheritDoc} */
//    @Override
//    public abstract List<IVariable> getChildVariables(IAesiThread thread, IAesiStackFrame stackFrame, IScope scope, IVariable variable);
}
