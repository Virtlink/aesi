package com.virtlink.aesi.debugging.mock;

import com.virtlink.aesi.debugging.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MockDebugSession extends AbstractDebugSession {

    private final MockThread thread = new MockThread();
    /** The stack with return 'addresses'. */
    private final Stack<SourceLocation> stack = new Stack<>();
    /** The current file that we are 'debugging' */
    private Path currentFile;
    /** The next line that will be 'executed' */
    private int currentLine = 0;
    /** The source files */
    private final Map<Path, SourceFile> sourceFiles;

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
     * Initializes a new instance of the {@link MockDebugSession} class.
     *
     * @param mainFile The main file, where execution begins.
     * @param sourceFiles The source files in the project, including the main file.
     */
    public MockDebugSession(Path mainFile, List<Path> sourceFiles) {
        this.currentFile = mainFile;
        this.currentLine = 0;
        this.sourceFiles = new HashMap<>();
        for (Path path : sourceFiles) {
            // Put a null value in the map, which we'll replace when initializing.
            this.sourceFiles.put(path, null);
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<IAesiThread> getThreads() {
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
    public void initialize() {
//        CompletableFuture.runAsync(this::initializeAsync)
//            .thenRun(() -> fireThreadInitialized(this.thread));
    }

    /** {@inheritDoc} */
    @Override
    public void suspend(IAesiThread thread) {
        fireThreadSuspended(this.thread, SuspendEventCause.Client);
    }

    /** {@inheritDoc} */
    @Override
    public void resume(IAesiThread thread) {
        fireThreadResumed(this.thread, SuspendEventCause.Client);
    }

//    /** {@inheritDoc} */
//    @Override
//    public void resumeBackward(IAesiThread thread) {
//        fireThreadResumed(this.thread, SuspendEventCause.Client);
//    }

    /** {@inheritDoc} */
    @Override
    public void terminate(IAesiThread thread) {
        fireThreadTerminated(thread);
    }

    /** {@inheritDoc} */
    @Override
    public void stepIn(IAesiThread thread) {
        fireThreadSuspended(this.thread, SuspendEventCause.StepIn);
        fireThreadResumed(this.thread, SuspendEventCause.StepIn);
    }

    /** {@inheritDoc} */
    @Override
    public void stepOut(IAesiThread thread) {
        fireThreadSuspended(this.thread, SuspendEventCause.StepOut);
        fireThreadResumed(this.thread, SuspendEventCause.StepOut);
    }

    /** {@inheritDoc} */
    @Override
    public void step(IAesiThread thread) {
        fireThreadSuspended(this.thread, SuspendEventCause.Step);
        fireThreadResumed(this.thread, SuspendEventCause.Step);
    }

//    /** {@inheritDoc} */
//    @Override
//    public void stepBack(IAesiThread thread) {
//        fireThreadSuspended(this.thread, SuspendEventCause.StepBack);
//        fireThreadResumed(this.thread, SuspendEventCause.StepBack);
//    }

    /** {@inheritDoc} */
    @Override
    public void skipTo(IAesiThread thread) {

    }

    /**
     * Initializes.
     */
    private void initializeAsync() {

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
    }

    /**
     * Evaluates a line.
     */
    private void evaluateLine() {
        // Before evaluation:
        // Were we stepping? Suspend!
        // Were we stepping into, out of? Suspend?

        SuspendEventCause suspendEventCause = getLineTriggers();

        // Evaluation.

        // After evaluation:
        @Nullable SourceLocation next = nextLine();
        if (next == null) {
            // EXTERMINATE
            fireThreadTerminated(this.thread);
            return;
        }
        this.currentFile = next.path;
        this.currentLine = next.line;

        // Suspension?
    }

    /**
     * Determines whether any breakpoints or exceptions cause a suspend event
     * for the current line.
     *
     * @return A {@link SuspendEventCause}, or {@link SuspendEventCause#None}
     * when there was no such cause.
     */
    private SuspendEventCause getLineTriggers() {
        Optional<MockBreakpoint> breakpoint = this.sourceFiles.get(this.currentFile).breakpoints.stream()
                .filter(b -> b.location.path.equals(this.currentFile) && b.location.line == this.currentLine)
                .findFirst();
        if (breakpoint.isPresent()) {
            // Trigger the breakpoint.
            return SuspendEventCause.Breakpoint;
        }
        String lineString = this.sourceFiles.get(this.currentFile).lines.get(this.currentLine);
        if (lineString.contains("exception")) {
            // Trigger an exception.
            return SuspendEventCause.Exception;
        }

        // Nothing to trigger.
        return SuspendEventCause.None;
    }

    /**
     * Determines the next line to 'execute'.
     *
     * @return The source location of the next line; or null when the program ends.
     */
    @Nullable
    private SourceLocation nextLine() {
        int nextLine = this.currentLine + 1;
        if (nextLine >= this.sourceFiles.get(this.currentFile).lines.size()) {
            // We've reached the end of the current file.
            if (this.stack.empty()) {
                // EXIT: We've reached the end of the program.
                return null;
            } else {
                // RETURN: Return to the previous file.
                return this.stack.pop();
            }
        } else {
            // We've reached the next line.
            String lineString = this.sourceFiles.get(this.currentFile).lines.get(this.currentLine);
            @Nullable SourceLocation nextFile = tryGetFilenameOnLine(lineString);
            if (nextFile != null) {
                // Jump to another file.
                this.stack.push(new SourceLocation(this.currentFile, nextLine));
                return nextFile;
            } else {
                // Jump to next line.
                return new SourceLocation(this.currentFile, nextLine);
            }
        }
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
