package com.virtlink.aesi.structureoutline;

import com.virtlink.aesi.Span;

public class Symbol implements ISymbol {
    private final String name;
    private final SymbolKind kind;
    private final Span location;

    @Override
    public String getName() { return this.name; }

    @Override
    public SymbolKind getKind() { return this.kind; }

    @Override
    public Span getLocation() { return this.location; }

    public Symbol(String name, SymbolKind kind, Span location) {
        this.name = name;
        this.kind = kind;
        this.location = location;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
