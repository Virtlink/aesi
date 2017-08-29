package com.virtlink.aesi.debugging.mock;

import com.virtlink.aesi.debugging.*;
import com.virtlink.aesi.eclipse.logging.LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MockDebugger extends AbstractAsyncDebugger2 {

    private static final Logger log = LoggerFactory.getLogger(MockDebugger.class);
    private final MockThread thread = new MockThread();
    /** The stack with return 'addresses'. */
    private final Stack<SourceLocation> stack = new Stack<>();
    /** The source files */
    private final Map<Path, SourceFile> sourceFiles;
    /** Condition for the next suspension. */
    private volatile SuspendEventCause suspensionCondition = SuspendEventCause.None;
    /** Whether to skip the suspension test before the next line. (Because we've already done that.) */
    private volatile boolean skipBeforeExecute = false;
    /** Whether we found a STEP IN or STEP OUT and have to suspend before executing the next line. */
    private volatile boolean stepConditionMet = false;

    /** Describes a source file. */
    private class SourceFile {
        /** The path of the source file. */
        public final Path path;
        /** The lines in the source file. */
        public final List<String> lines;
        /** The breakpoints in the source file. */
        public final List<MockBreakpoint> breakpoints = new ArrayList<>();

        public SourceFile(Path path, List<String> lines) {
            this.path = path;
            this.lines = lines;
        }
    }

    /**
     * Initializes a new instance of the {@link MockDebugger} class.
     *
     * @param mainFile The main file, where execution begins.
     * @param sourceFiles The source files in the project, including the main file.
     */
    public MockDebugger(Path mainFile, List<Path> sourceFiles) {
        this.sourceFiles = new HashMap<>();
        for (Path path : sourceFiles) {
            // Put a null value in the map, which we'll replace when initializing.
            this.sourceFiles.put(path, null);
        }

        this.stack.push(new SourceLocation(mainFile, 0));
    }

    /** {@inheritDoc} */
    @Override
    protected List<IAesiThread> doGetThreads() {
        // We support only one thread.
        return Collections.singletonList(this.thread);
    }

    /** {@inheritDoc} */
    @Override
    public List<IAesiStackFrame> getStackFrames(IAesiThread thread) {
        // TODO
        return Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Override
    public List<IScope> getScopes(IAesiThread thread, IAesiStackFrame stackFrame) {
        // TODO
        return Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Override
    public List<IVariable> getVariables(IAesiThread thread, IAesiStackFrame stackFrame, IScope scope) {
        // TODO
        return Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Override
    public List<IVariable> getChildVariables(IAesiThread thread, IAesiStackFrame stackFrame, IScope scope, IVariable variable) {
        // TODO
        return Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Override
    protected void initializeAsync() {
        for (Path path : this.sourceFiles.keySet()) {
            SourceFile sourceFile = this.sourceFiles.get(path);
            if (sourceFile != null)
                continue;

            List<String> lines;
            try {
                lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            sourceFile = new SourceFile(path, lines);
            this.sourceFiles.put(path, sourceFile);
        }

        fireInitialized();
    }

    /** {@inheritDoc} */
    @Override
    protected void terminateAsync() {
        fireTerminated();
    }

    /** {@inheritDoc} */
    @Override
    protected void attachAsync(IAttachOptions arguments) {
        fireAttached();
        if (arguments.shouldSuspend()) {
            suspendExecution(SuspendEventCause.Client);
        } else {
            resume(this.thread);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void detachAsync(IDetachOptions arguments) {
        fireDetached();
    }

    /** {@inheritDoc} */
    @Override
    protected void suspendAsync(IAesiThread thread) {
        this.suspensionCondition = SuspendEventCause.Client;
    }

    /** {@inheritDoc} */
    @Override
    protected void resumeAsync(IAesiThread thread) {
        this.suspensionCondition = SuspendEventCause.None;
        fireThreadResumed(thread, SuspendEventCause.Client);
        executeAll();
    }

    /** {@inheritDoc} */
    @Override
    protected void stepAsync(IAesiThread thread) {
        this.suspensionCondition = SuspendEventCause.Step;
        fireThreadResumed(thread, SuspendEventCause.Step);
        executeAll();
    }

    /** {@inheritDoc} */
    @Override
    protected void stepInAsync(IAesiThread thread) {
        this.suspensionCondition = SuspendEventCause.StepIn;
        fireThreadResumed(thread, SuspendEventCause.StepIn);
        executeAll();
    }

    /** {@inheritDoc} */
    @Override
    protected void stepOutAsync(IAesiThread thread) {
        this.suspensionCondition = SuspendEventCause.StepOut;
        fireThreadResumed(thread, SuspendEventCause.StepOut);
        executeAll();
    }

    /**
     * Executes lines until suspended or terminated.
     */
    private void executeAll() {
        boolean cont;
        do {
            // Determine whether to suspend.
            if (!this.skipBeforeExecute) {
                if (!this.stack.empty()) {
                    SourceLocation location = this.stack.peek();
                    log.debug(".. " + location.path.getFileName() + ":" + location.line);
                }

                SuspendEventCause suspension = beforeExecute();
                if (suspension != SuspendEventCause.None) {
                    this.skipBeforeExecute = true;
                    suspendExecution(suspension);
                    return;
                }
            }
            this.skipBeforeExecute = false;

            if (!this.stack.empty()) {
                SourceLocation location = this.stack.peek();
                log.debug("EX " + location.path.getFileName() + ":" + location.line);
            }

            // Execute.
            cont = execute();
        } while(cont);
    }

    /**
     * Tests whether we need to suspend before executing the next line.
     *
     * Reasons to suspend include:
     * - step completed
     * - client suspends execution
     * - breakpoint hit
     *
     * @return A reason to suspend;
     * or {@link SuspendEventCause#None} when there is no reason to suspend.
     */
    private SuspendEventCause beforeExecute() {
        if (this.stack.empty())
            return SuspendEventCause.None;
        SourceLocation location = this.stack.peek();

        SuspendEventCause cause = SuspendEventCause.None;

        if (stepConditionMet &&
                (this.suspensionCondition == SuspendEventCause.StepOut ||
                this.suspensionCondition == SuspendEventCause.StepIn)) {
            // Suspend because we hit a STEP IN or STEP OUT.
            cause = this.suspensionCondition;
        }
        else if (this.suspensionCondition == SuspendEventCause.Client ||
                this.suspensionCondition == SuspendEventCause.Step) {
            // Suspend by user request or STEP.
            cause = this.suspensionCondition;
        } else {
            @Nullable MockBreakpoint breakpoint = getBreakPoint(location);
            if (breakpoint != null) {
                // Suspend because of a BREAKPOINT.
                cause = SuspendEventCause.Breakpoint;
            }
        }

        this.stepConditionMet = false;
        this.suspensionCondition = SuspendEventCause.None;
        return cause;
    }

    /**
     * Executes a single line.
     *
     * The start address should be put on top of the stack before
     * this method is called.
     *
     * If there's a reason to suspend (e.g. an exception is thrown)
     * we suspend here.
     *
     * @return Whether to continue. {@code false} when terminated.
     */
    private boolean execute() {
        if (this.stack.empty()) {
            // EXTERMINATE
            log.debug("TERMINATE");
            // Nothing to execute. We're done here.
            fireThreadDestroyed(this.thread);
            fireTerminated();
            return false;
        }

        // Get the line to execute next from the top of the stack.
        SourceLocation location = this.stack.pop();
        @Nullable String line = getLine(location);
        if (line == null) {
            // STEP OUT
        	if (!this.stack.empty())
        		log.debug("<- STEP OUT " + this.stack.peek());
            // We've reached the end of the file.
            // NOTE: Nothing to push on the stack.
            if (suspensionCondition == SuspendEventCause.StepOut) {
                // Suspend before next line.
                this.stepConditionMet = true;
            }
        } else {
            // We're going to the next line (possibly after a STEP IN and STEP OUT).
            this.stack.push(new SourceLocation(location.path, location.line + 1));
            log.debug("-> " + this.stack.peek());

            @Nullable SourceLocation next = tryGetFilenameOnLine(line);
            if (next != null) {
                // STEP IN
                log.debug("=> STEP INTO " + next);
                // We're going into another file.
                this.stack.push(next);
                if (suspensionCondition == SuspendEventCause.StepIn) {
                    // Suspend before next line.
                    this.stepConditionMet = true;
                }
            }

            if (line.contains("exception")) {
                // EXCEPTION
                // Trigger an exception.
                // Suspend on this instruction.
                // NOTE: Since we've pushed the next line already on the stack,
                // execution automatically continues with the next line.
                suspendExecution(SuspendEventCause.Exception);
                return false;
            }
        }

        return true;
    }

    /**
     * Gets a line from a file.
     *
     * @param location The source location.
     * @return The line; or null when there is none (EOF).
     */
    @Nullable
    private String getLine(SourceLocation location) {
        SourceFile sourceFile = this.sourceFiles.getOrDefault(location.path, null);
        if (sourceFile == null)
            return null;
        if (location.line >= sourceFile.lines.size())
            return null;
        return sourceFile.lines.get(location.line);
    }

    /**
     * Gets the first breakpoint at the source location.
     * @param location The source location.
     * @return The breakpoint; or null when there is none.
     */
    @Nullable
    private MockBreakpoint getBreakPoint(SourceLocation location) {
        Optional<MockBreakpoint> breakpoint = this.sourceFiles.get(location.path).breakpoints.stream()
                .filter(b -> b.location.path.equals(location.path) && b.location.line == location.line)
                .findFirst();
        return breakpoint.orElse(null);
    }

    /**
     * Suspends execution.
     *
     * @param cause The cause of the suspension.
     */
    private void suspendExecution(SuspendEventCause cause) {
        fireThreadSuspended(this.thread, cause);
    }

    /**
     * Gets the source location of a file named on the specified line;
     * or null when no file was named.
     *
     * @param line The line string.
     * @return The named file and line zero; or null.
     */
    @Nullable
    private SourceLocation tryGetFilenameOnLine(String line) {
        // TODO
        // If the line contains the name of another file in this project,
        // we return the filename and the line zero.
        // Otherwise, return null.
        return null;
    }
}
