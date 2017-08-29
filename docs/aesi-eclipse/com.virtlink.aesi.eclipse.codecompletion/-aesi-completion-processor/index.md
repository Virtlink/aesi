---
title: AesiCompletionProcessor - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.eclipse.codecompletion](../index.html) / [AesiCompletionProcessor](.)

# AesiCompletionProcessor

`open class AesiCompletionProcessor : IContentAssistProcessor`

A code completion processor for AESI languages.

### Constructors

| [&lt;init&gt;](-init-.html) | `AesiCompletionProcessor()`<br>A code completion processor for AESI languages. |

### Functions

| [computeCompletionProposals](compute-completion-proposals.html) | `open fun computeCompletionProposals(textViewer: ITextViewer, offset: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<ICompletionProposal>` |
| [computeContextInformation](compute-context-information.html) | `open fun computeContextInformation(textViewer: ITextViewer, offset: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<IContextInformation>` |
| [getCompletionProposalAutoActivationCharacters](get-completion-proposal-auto-activation-characters.html) | `open fun getCompletionProposalAutoActivationCharacters(): `[`CharArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-array/index.html) |
| [getContextInformationAutoActivationCharacters](get-context-information-auto-activation-characters.html) | `open fun getContextInformationAutoActivationCharacters(): `[`CharArray`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-array/index.html) |
| [getContextInformationValidator](get-context-information-validator.html) | `open fun getContextInformationValidator(): IContextInformationValidator` |
| [getErrorMessage](get-error-message.html) | `open fun getErrorMessage(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

