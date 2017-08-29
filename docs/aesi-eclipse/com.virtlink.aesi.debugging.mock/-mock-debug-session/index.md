---
title: MockDebugSession - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging.mock](../index.html) / [MockDebugSession](.)

# MockDebugSession

`open class MockDebugSession : AbstractDebugSession`

### Constructors

| [&lt;init&gt;](-init-.html) | `MockDebugSession(mainFile: Path, sourceFiles: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<Path>)`<br>Initializes a new instance of the MockDebugSession class. |

### Functions

| [getChildVariables](get-child-variables.html) | `open fun getChildVariables(thread: IAesiThread, stackFrame: IAesiStackFrame, scope: IScope, variable: IVariable): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IVariable>`<br>{@inheritDoc} |
| [getScopes](get-scopes.html) | `open fun getScopes(thread: IAesiThread, stackFrame: IAesiStackFrame): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IScope>`<br>{@inheritDoc} |
| [getStackFrames](get-stack-frames.html) | `open fun getStackFrames(thread: IAesiThread): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IAesiStackFrame>`<br>{@inheritDoc} |
| [getThreads](get-threads.html) | `open fun getThreads(): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IAesiThread>`<br>{@inheritDoc} |
| [getVariables](get-variables.html) | `open fun getVariables(thread: IAesiThread, stackFrame: IAesiStackFrame, scope: IScope): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IVariable>`<br>{@inheritDoc} |
| [initialize](initialize.html) | `open fun initialize(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [resume](resume.html) | `open fun resume(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [skipTo](skip-to.html) | `open fun skipTo(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [step](step.html) | `open fun step(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [stepIn](step-in.html) | `open fun stepIn(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [stepOut](step-out.html) | `open fun stepOut(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [suspend](suspend.html) | `open fun suspend(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [terminate](terminate.html) | `open fun terminate(thread: IAesiThread): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |

