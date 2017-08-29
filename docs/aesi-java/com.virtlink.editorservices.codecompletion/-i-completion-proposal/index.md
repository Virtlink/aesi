---
title: ICompletionProposal - aesi-java
---

[aesi-java](../../index.html) / [com.virtlink.editorservices.codecompletion](../index.html) / [ICompletionProposal](.)

# ICompletionProposal

`interface ICompletionProposal`

### Properties

| [attributes](attributes.html) | `abstract val attributes: `[`EnumSet`](http://docs.oracle.com/javase/6/docs/api/java/util/EnumSet.html)`<`[`Attribute`](../-attribute/index.html)`>` |
| [caret](caret.html) | `abstract val caret: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`?` |
| [caseSensitive](case-sensitive.html) | `abstract val caseSensitive: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [description](description.html) | `abstract val description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [documentation](documentation.html) | `abstract val documentation: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [insertionText](insertion-text.html) | `abstract val insertionText: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [kind](kind.html) | `abstract val kind: `[`Kind`](../../com.virtlink.editorservices/-kind/index.html) |
| [name](name.html) | `abstract val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [postfix](postfix.html) | `abstract val postfix: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [priority](priority.html) | `abstract val priority: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [type](type.html) | `abstract val type: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [visibility](visibility.html) | `abstract val visibility: `[`IVisibility`](../../com.virtlink.editorservices/-i-visibility/index.html)`?` |

### Inheritors

| [CompletionProposal](../-completion-proposal/index.html) | `class CompletionProposal : ICompletionProposal` |

