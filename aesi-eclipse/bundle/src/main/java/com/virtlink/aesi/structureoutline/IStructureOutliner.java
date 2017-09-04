package com.virtlink.aesi.structureoutline;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.ICancellationToken;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Provides an outline of the document structure.
 */
public interface IStructureOutliner {

    List<? extends ISymbol> getRoots(IAesiDocument document,
                                     @Nullable ICancellationToken cancellationToken);

    List<? extends ISymbol> getChildren(IAesiDocument document,
                                        ISymbol symbol,
                                        @Nullable ICancellationToken cancellationToken);

    boolean hasChildren(IAesiDocument document,
                        ISymbol symbol,
                        @Nullable ICancellationToken cancellationToken);

}
