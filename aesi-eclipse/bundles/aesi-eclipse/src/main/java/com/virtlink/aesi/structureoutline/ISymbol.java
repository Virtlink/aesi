package com.virtlink.aesi.structureoutline;

import com.virtlink.aesi.Span;

public interface ISymbol {

    String getName();
    SymbolKind getKind();
    Span getLocation();

}
