package com.virtlink.editorservices.eclipse.structureoutline;

import com.virtlink.editorservices.structureoutline.IStructureOutlineElement;

import java.net.URI;

import javax.annotation.Nullable;

public class AesiStructureNode {
    private final URI document;
    private final IStructureOutlineElement node;
    @Nullable
    private final AesiStructureNode parent;

    public URI getDocumentUri() { return this.document; }
    public IStructureOutlineElement getNode() { return this.node; }
    @Nullable public AesiStructureNode getParent() { return this.parent; }

    public AesiStructureNode(URI document, IStructureOutlineElement node, @Nullable AesiStructureNode parent) {
        this.document = document;
        this.node = node;
        this.parent = parent;
    }
}
