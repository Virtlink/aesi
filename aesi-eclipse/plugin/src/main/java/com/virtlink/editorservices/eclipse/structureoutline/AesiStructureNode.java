package com.virtlink.editorservices.eclipse.structureoutline;

import com.virtlink.editorservices.structureoutline.IStructureTreeNode;

import java.net.URI;

import javax.annotation.Nullable;

public class AesiStructureNode {
    private final URI document;
    private final IStructureTreeNode node;
    @Nullable
    private final AesiStructureNode parent;

    public URI getDocumentUri() { return this.document; }
    public IStructureTreeNode getNode() { return this.node; }
    @Nullable public AesiStructureNode getParent() { return this.parent; }

    public AesiStructureNode(URI document, IStructureTreeNode node, @Nullable AesiStructureNode parent) {
        this.document = document;
        this.node = node;
        this.parent = parent;
    }
}
