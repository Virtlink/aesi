package com.virtlink.aesi.codecompletion;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.ICancellationToken;
import com.virtlink.aesi.IAesiProject;
import com.virtlink.aesi.Location;
import com.virtlink.editorservices.*;
import com.virtlink.editorservices.codecompletion.Attribute;
import com.virtlink.editorservices.codecompletion.CompletionProposal;
import com.virtlink.editorservices.codecompletion.ICompletionProposal;

import javax.annotation.Nullable;

public class DummyCodeCompleter implements ICodeCompleter {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ICompletionProposal> complete(
            IAesiDocument document,
            int caretOffset,
            @Nullable ICancellationToken cancellationToken) {
        List<ICompletionProposal> proposals = new ArrayList<>();
        proposals.add(new CompletionProposal("Proposal1", "Description", "Documentation", false, "insertion1", 0, null, null, 0, Kind.Method, new Visibility(ClassVisibility.Private, PackageVisibility.Package, LibraryVisibility.Internal), EnumSet.of(Attribute.Static)));
        proposals.add(new CompletionProposal("Another prposal", "Description 2", "Documentation 2", false, "another2", 0, null, null, 0, Kind.Method, new Visibility(ClassVisibility.Private, PackageVisibility.Package, LibraryVisibility.Internal), EnumSet.of(Attribute.Static)));
        return proposals;
    }
}
