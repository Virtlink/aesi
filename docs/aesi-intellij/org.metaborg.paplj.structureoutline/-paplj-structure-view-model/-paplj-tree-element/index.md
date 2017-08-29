---
title: PapljStructureViewModel.PapljTreeElement - aesi-intellij
---

[aesi-intellij](../../../index.html) / [org.metaborg.paplj.structureoutline](../../index.html) / [PapljStructureViewModel](../index.html) / [PapljTreeElement](.)

# PapljTreeElement

`class PapljTreeElement<T : PsiElement> : PsiTreeElementBase<T>`

### Constructors

| [&lt;init&gt;](-init-.html) | `PapljTreeElement(element: T, symbol: ISymbol?, model: `[`PapljStructureViewModel`](../index.html)`)` |

### Properties

| [model](model.html) | `val model: `[`PapljStructureViewModel`](../index.html) |
| [symbol](symbol.html) | `val symbol: ISymbol?` |

### Functions

| [getChildrenBase](get-children-base.html) | `fun getChildrenBase(): `[`MutableCollection`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-collection/index.html)`<StructureViewTreeElement>` |
| [getIcon](get-icon.html) | `fun getIcon(open: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Icon`](http://docs.oracle.com/javase/6/docs/api/javax/swing/Icon.html)`?` |
| [getPresentableText](get-presentable-text.html) | `fun getPresentableText(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |

