package com.virtlink.aesi.debugging;

import java.net.URI;

/**
 * Options for attaching to a program.
 */
public interface IAttachOptions {

    /**
     * Gets the program's URI.
     *
     * The interpretation of this URI is left to the debugger.
     * It may signify an executable, a running process, the root folder of a project,
     * etc. The URI is set by the editor's language plugin, such that the editor's plugin
     * and the debugger can agree on what kinds of programs and URIs to support.
     *
     * The decision whether to launch a program or attach to an already running program
     * is also determined by the interpretation of this URI. For example, an URI
     * denoting a process or socket indicates that the debugger should be attached,
     * whereas an executable or program root folder indicates that the debugger should
     * launch the program as well.
     *
     * @return The program's URI.
     */
    URI getUri();

    /**
     * Gets whether the program should be running or suspended when the debugger is attached.
     *
     * @return {@code true} to suspend the program when the debugger is attached;
     * otherwise, {@code false} to have the program running when the debugger is attached.
     */
    boolean shouldSuspend();
}
