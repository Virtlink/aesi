package com.virtlink.aesi.eclipse.structureoutline;

import com.virtlink.editorservices.IDocument;
import com.virtlink.editorservices.IProject;
import com.virtlink.editorservices.structureoutline.IStructureTreeNode;

import javax.annotation.Nullable;

public class AesiStructureNode {
    private final IProject project;
    private final IDocument document;
    private final IStructureTreeNode node;
    @Nullable
    private final AesiStructureNode parent;

    public IProject getProject() { return this.project; }
    public IDocument getDocument() { return this.document; }
    public IStructureTreeNode getNode() { return this.node; }
    @Nullable public AesiStructureNode getParent() { return this.parent; }

    public AesiStructureNode(IProject project, IDocument document, IStructureTreeNode node, @Nullable AesiStructureNode parent) {
        this.project = project;
        this.document = document;
        this.node = node;
        this.parent = parent;
    }
}
