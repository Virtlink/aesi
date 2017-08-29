package com.virtlink.aesi.structureoutline;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.ICancellationToken;
import com.virtlink.aesi.Span;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DummyStructureOutliner implements IStructureOutliner {

    private final SymbolDef rootSymbol = new SymbolDef("root", SymbolKind.Constant, Span.of(0, 2), Arrays.asList(
            new SymbolDef("file1", SymbolKind.File, Span.of(0, 2), Arrays.asList(
                    new SymbolDef("myClass0", SymbolKind.Class, Span.of(1, 2), Arrays.asList(
                            new SymbolDef("myFunc", SymbolKind.Function, Span.of(2, 2), Collections.emptyList())
                    )),
                    new SymbolDef("myClass1", SymbolKind.Class, Span.of(3, 2), Collections.emptyList()),
                    new SymbolDef("myClass2", SymbolKind.Class, Span.of(4, 2), Collections.emptyList()),
                    new SymbolDef("myClass4", SymbolKind.Class, Span.of(5, 2), Collections.emptyList())
            )),
            new SymbolDef("file2", SymbolKind.File, Span.of(6, 2), Arrays.asList(
                    new SymbolDef("anotherFunc", SymbolKind.Function, Span.of(7, 2), Collections.emptyList())
            ))
    ));

    @Override
    public List<? extends ISymbol> getRoots(IAesiDocument document, @Nullable ICancellationToken cancellationToken) {
        return Arrays.asList(rootSymbol);
    }

    @Override
    public List<? extends ISymbol> getChildren(IAesiDocument document, ISymbol symbol, @Nullable ICancellationToken cancellationToken) {
        return ((SymbolDef)symbol).getChildren();
    }

    @Override
    public boolean hasChildren(IAesiDocument document, ISymbol symbol, @Nullable ICancellationToken cancellationToken) {
        return getChildren(document, symbol, cancellationToken).size() > 0;
    }

    private final class SymbolDef extends Symbol {
        private final List<SymbolDef> children;

        public List<SymbolDef> getChildren() { return this.children; }

        public SymbolDef(String name, SymbolKind kind, Span location, List<SymbolDef> children) {
            super(name, kind, location);

            this.children = children;
        }
    }
}
