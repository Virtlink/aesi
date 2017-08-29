---
title: PapljStructureViewModel - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.structureoutline](../index.html) / [PapljStructureViewModel](.)

# PapljStructureViewModel

`class PapljStructureViewModel : TextEditorBasedStructureViewModel`

### Types

| [PapljTreeElement](-paplj-tree-element/index.html) | `class PapljTreeElement<T : PsiElement> : PsiTreeElementBase<T>` |

### Constructors

| [&lt;init&gt;](-init-.html) | `PapljStructureViewModel(editor: Editor?, file: `[`PapljFile`](../../org.metaborg.paplj.psi/-paplj-file/index.html)`, outliner: `[`IStructureOutliner`](https://virtlink.com/aesi/aesi-java/com.virtlink.editorservices.structureoutline/-i-structure-outliner/index.html)`, project: `[`IProject`](https://virtlink.com/aesi/aesi-java/com.virtlink.editorservices/-i-project/index.html)`, document: `[`IDocument`](https://virtlink.com/aesi/aesi-java/com.virtlink.editorservices/-i-document/index.html)`)` |

### Properties

| [document](document.html) | `val document: `[`IDocument`](https://virtlink.com/aesi/aesi-java/com.virtlink.editorservices/-i-document/index.html) |
| [outliner](outliner.html) | `val outliner: `[`IStructureOutliner`](https://virtlink.com/aesi/aesi-java/com.virtlink.editorservices.structureoutline/-i-structure-outliner/index.html) |
| [project](project.html) | `val project: `[`IProject`](https://virtlink.com/aesi/aesi-java/com.virtlink.editorservices/-i-project/index.html) |

### Functions

| [getPsiFile](get-psi-file.html) | `fun getPsiFile(): `[`PapljFile`](../../org.metaborg.paplj.psi/-paplj-file/index.html) |
| [getRoot](get-root.html) | `fun getRoot(): StructureViewTreeElement` |

