---
title: MockDebugger - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging.mock](../index.html) / [MockDebugger](.)

# MockDebugger

`open class MockDebugger : AbstractAsyncDebugger2`

### Constructors

| [&lt;init&gt;](-init-.html) | `MockDebugger(mainFile: Path, sourceFiles: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<Path>)`<br>Initializes a new instance of the MockDebugger class. |

### Functions

| [getChildVariables](get-child-variables.html) | `open fun getChildVariables(thread: IAesiThread, stackFrame: IAesiStackFrame, scope: IScope, variable: IVariable): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IVariable>`<br>{@inheritDoc} |
| [getScopes](get-scopes.html) | `open fun getScopes(thread: IAesiThread, stackFrame: IAesiStackFrame): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IScope>`<br>{@inheritDoc} |
| [getStackFrames](get-stack-frames.html) | `open fun getStackFrames(thread: IAesiThread): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IAesiStackFrame>`<br>{@inheritDoc} |
| [getVariables](get-variables.html) | `open fun getVariables(thread: IAesiThread, stackFrame: IAesiStackFrame, scope: IScope): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IVariable>`<br>{@inheritDoc} |

