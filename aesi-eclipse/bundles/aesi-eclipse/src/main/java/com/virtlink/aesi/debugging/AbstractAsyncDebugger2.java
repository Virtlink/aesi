package com.virtlink.aesi.debugging;

import java.util.concurrent.CompletableFuture;

/**
 * Abstract base class for asynchronous debuggers.
 */
public abstract class AbstractAsyncDebugger2 extends AbstractDebugger {

    /**
     * Initializes a new instance of the {@link AbstractAsyncDebugger2} class.
     */
    protected AbstractAsyncDebugger2() {
        super();
    }

    /**
     * Asynchronously performs the `initialize` action
     *
     * @return The future.
     */
    protected abstract void initializeAsync();

    /**
     * Asynchronously performs the `terminate` action.
     */
    protected abstract void terminateAsync();

    /**
     * Asynchronously performs the `attach` action.
     *
     * @param arguments The attach arguments.
     */
    protected abstract void attachAsync(IAttachOptions arguments);

    /**
     * Asynchronously performs the `detach` action.
     *
     * @param arguments The detach arguments.
     */
    protected abstract void detachAsync(IDetachOptions arguments);

    /**
     * Asynchronously performs the `suspend` action on the specified thread.
     *
     * @param thread The thread.
     */
    protected abstract void suspendAsync(IAesiThread thread);

    /**
     * Asynchronously performs the `resume` action on the specified thread.
     *
     * @param thread The thread.
     */
    protected abstract void resumeAsync(IAesiThread thread);

    /**
     * Asynchronously performs the `step` action on the specified thread.
     *
     * @param thread The thread.
     */
    protected abstract void stepAsync(IAesiThread thread);

    /**
     * Asynchronously performs the `step in` action on the specified thread.
     *
     * @param thread The thread.
     */
    protected abstract void stepInAsync(IAesiThread thread);

    /**
     * Asynchronously performs the `step out` action on the specified thread.
     *
     * @param thread The thread
     * @return The future.
     */
    protected abstract void stepOutAsync(IAesiThread thread);

    /** {@inheritDoc} */
    @Override
    protected final void doInitialize() {
        CompletableFuture.runAsync(this::initializeAsync);
    }

    /** {@inheritDoc} */
    @Override
    protected final void doTerminate() {
        CompletableFuture.runAsync(this::terminateAsync);
    }

    /** {@inheritDoc} */
    @Override
    protected final void doAttach(IAttachOptions arguments) {
        CompletableFuture.runAsync(() -> attachAsync(arguments));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doDetach(IDetachOptions arguments) {
        CompletableFuture.runAsync(() -> detachAsync(arguments));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doSuspend(IAesiThread thread) {
        CompletableFuture.runAsync(() -> suspendAsync(thread));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doResume(IAesiThread thread) {
        CompletableFuture.runAsync(() -> resumeAsync(thread));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doStep(IAesiThread thread) {
        CompletableFuture.runAsync(() -> stepAsync(thread));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doStepIn(IAesiThread thread) {
        CompletableFuture.runAsync(() -> stepInAsync(thread));
    }

    /** {@inheritDoc} */
    @Override
    protected final void doStepOut(IAesiThread thread) {
        CompletableFuture.runAsync(() -> stepOutAsync(thread));
    }
}
