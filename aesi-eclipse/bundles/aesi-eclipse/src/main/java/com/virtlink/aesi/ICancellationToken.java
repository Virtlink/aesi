package com.virtlink.aesi;

/**
 * A cancellation token.
 */
public interface ICancellationToken {

    /**
     * Gets whether the operation has been cancelled.
     * @return {@code true} when the operation has been cancelled;
     * otherwise, {@code false}.
     */
    boolean isCancelled();

}
