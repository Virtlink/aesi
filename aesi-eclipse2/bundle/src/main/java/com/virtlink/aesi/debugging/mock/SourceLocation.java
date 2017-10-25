package com.virtlink.aesi.debugging.mock;

import java.nio.file.Path;

/** Describes a source location. */
class SourceLocation {
    /** The path of the source file. */
    public final Path path;
    /** The zero-based line of the source file. */
    public final int line;

    public SourceLocation(Path path, int line) {
        this.path = path;
        this.line = line;
    }

    @Override
    public String toString() {
        return path.getFileName() + ":" + line;
    }
}