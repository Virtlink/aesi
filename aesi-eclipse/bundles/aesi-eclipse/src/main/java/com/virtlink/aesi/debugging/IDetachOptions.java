package com.virtlink.aesi.debugging;

/**
 * Options for detaching from a program.
 */
public interface IDetachOptions {

    /**
     * Gets whether the debugger should terminate the program.
     *
     * @return {@code true} to leave the program running;
     * otherwise, {@code false} to terminate the program.
     */
    boolean keepAlive();
}
