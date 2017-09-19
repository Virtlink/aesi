package com.virtlink.paplj;

import com.virtlink.editorservices.*;
import com.virtlink.editorservices.codecompletion.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DummyCodeCompleter implements ICodeCompleter {

    @NotNull
    @Override
    public ICompletionInfo complete(IDocument iDocument, int i, ICancellationToken iCancellationToken) {
        List<ICompletionProposal> proposals = new ArrayList<>();
        proposals.add(new CompletionProposal("Proposal1", "Description", "Documentation", false, "insertion1", 0, null, null, 0, Kind.Method, new Visibility(ClassVisibility.Private, PackageVisibility.Package, LibraryVisibility.Internal), EnumSet.of(Attribute.Static)));
        proposals.add(new CompletionProposal("Proposal 2 (another prposal)", "Description 2", "Documentation 2", false, "another2", 0, null, null, 0, Kind.Method, new Visibility(ClassVisibility.Private, PackageVisibility.Package, LibraryVisibility.Internal), EnumSet.of(Attribute.Static)));

        // TODO: Determine prefix according to language rules.
        return new CompletionInfo("Pro", proposals);
    }
}
