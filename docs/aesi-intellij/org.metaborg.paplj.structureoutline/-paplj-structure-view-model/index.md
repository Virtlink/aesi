---
title: PapljStructureViewModel - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.structureoutline](../index.html) / [PapljStructureViewModel](.)

# PapljStructureViewModel

`class PapljStructureViewModel : TextEditorBasedStructureViewModel`

### Types

| [PapljTreeElement](-paplj-tree-element/index.html) | `class PapljTreeElement<T : PsiElement> : PsiTreeElementBase<T>` |

### Constructors

| [&lt;init&gt;](-init-.html) | `PapljStructureViewModel(editor: Editor?, file: `[`PapljFile`](../../org.metaborg.paplj.psi/-paplj-file/index.html)`, outliner: IStructureOutliner, project: IProject, document: IDocument)` |

### Properties

| [document](document.html) | `val document: IDocument` |
| [outliner](outliner.html) | `val outliner: IStructureOutliner` |
| [project](project.html) | `val project: IProject` |

### Functions

| [getPsiFile](get-psi-file.html) | `fun getPsiFile(): `[`PapljFile`](../../org.metaborg.paplj.psi/-paplj-file/index.html) |
| [getRoot](get-root.html) | `fun getRoot(): StructureViewTreeElement` |

