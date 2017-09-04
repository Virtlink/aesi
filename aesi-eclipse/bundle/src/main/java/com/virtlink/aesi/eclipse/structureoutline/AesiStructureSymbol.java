package com.virtlink.aesi.eclipse.structureoutline;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.structureoutline.ISymbol;

import javax.annotation.Nullable;

public class AesiStructureSymbol {
    private final IAesiDocument document;
    private final ISymbol symbol;
    @Nullable
    private final AesiStructureSymbol parent;

    public IAesiDocument getDocument() { return this.document; }
    public ISymbol getSymbol() { return this.symbol; }
    @Nullable public AesiStructureSymbol getParent() { return this.parent; }

    public AesiStructureSymbol(IAesiDocument document, ISymbol symbol, @Nullable AesiStructureSymbol parent) {
        this.document = document;
        this.symbol = symbol;
        this.parent = parent;
    }
}
