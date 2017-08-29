package com.virtlink.aesi.debugging;

/**
 * Specifies the capabilities of the debugger.
 */
public interface IDebuggerCapabilities {

    /**
     * Gets whether the debugger supports being reinitialized.
     *
     * @return {@code true} when the debugger supports being reinitialized;
     * otherwise, {@code false}.
     */
    boolean canReinitialize();

}
