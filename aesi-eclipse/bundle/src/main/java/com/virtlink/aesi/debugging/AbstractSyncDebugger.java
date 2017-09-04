package com.virtlink.aesi.debugging;

import java.util.concurrent.CompletableFuture;

/**
 * Executes synchronous actions asynchronously.
 */
public abstract class AbstractSyncDebugger extends AbstractAsyncDebugger {

    protected abstract void initializeSync();
    protected abstract void terminateSync();
    protected abstract void attachSync(IAttachOptions arguments);
    protected abstract void detachSync(IDetachOptions arguments);
    protected abstract void suspendSync(IAesiThread thread);
    protected abstract void resumeSync(IAesiThread thread);
    protected abstract void stepSync(IAesiThread thread);
    protected abstract void stepInSync(IAesiThread thread);
    protected abstract void stepOutSync(IAesiThread thread);

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> initializeAsync() {
        return CompletableFuture.runAsync(this::initializeSync);
    }

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> terminateAsync() {
        return CompletableFuture.runAsync(this::terminateSync);
    }

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> attachAsync(IAttachOptions arguments) {
        return CompletableFuture.runAsync(() -> attachSync(arguments));
    }

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> detachAsync(IDetachOptions arguments) {
        return CompletableFuture.runAsync(() -> detachSync(arguments));
    }

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> suspendAsync(IAesiThread thread) {
        return CompletableFuture.runAsync(() -> suspendSync(thread));
    }

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> resumeAsync(IAesiThread thread) {
        return CompletableFuture.runAsync(() -> resumeSync(thread));
    }

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> stepAsync(IAesiThread thread) {
        return CompletableFuture.runAsync(() -> stepSync(thread));
    }

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> stepInAsync(IAesiThread thread) {
        return CompletableFuture.runAsync(() -> stepInSync(thread));
    }

    /** {@inheritDoc} */
    @Override
    protected final CompletableFuture<Void> stepOutAsync(IAesiThread thread) {
        return CompletableFuture.runAsync(() -> stepOutSync(thread));
    }

}
