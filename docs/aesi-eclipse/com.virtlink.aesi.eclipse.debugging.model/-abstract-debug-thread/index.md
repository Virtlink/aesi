---
title: AbstractDebugThread - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.eclipse.debugging.model](../index.html) / [AbstractDebugThread](.)

# AbstractDebugThread

`abstract class AbstractDebugThread : DebugElement, IThread`

Abstract base class for debug thread implementations.

### Functions

| [canResume](can-resume.html) | `open fun canResume(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [canStepInto](can-step-into.html) | `open fun canStepInto(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [canStepOver](can-step-over.html) | `open fun canStepOver(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [canStepReturn](can-step-return.html) | `open fun canStepReturn(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [canSuspend](can-suspend.html) | `open fun canSuspend(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [canTerminate](can-terminate.html) | `open fun canTerminate(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [getBreakpoints](get-breakpoints.html) | `abstract fun getBreakpoints(): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<IBreakpoint>`<br>{@inheritDoc} |
| [getModelIdentifier](get-model-identifier.html) | `open fun getModelIdentifier(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>{@inheritDoc} |
| [getName](get-name.html) | `abstract fun getName(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>{@inheritDoc} |
| [getPriority](get-priority.html) | `open fun getPriority(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>{@inheritDoc} |
| [getStackFrames](get-stack-frames.html) | `abstract fun getStackFrames(): `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<IStackFrame>`<br>{@inheritDoc} |
| [getTopStackFrame](get-top-stack-frame.html) | `open fun getTopStackFrame(): IStackFrame?`<br>{@inheritDoc} |
| [hasStackFrames](has-stack-frames.html) | `open fun hasStackFrames(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [isStepping](is-stepping.html) | `abstract fun isStepping(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [isSuspended](is-suspended.html) | `abstract fun isSuspended(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [isTerminated](is-terminated.html) | `abstract fun isTerminated(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>{@inheritDoc} |
| [resume](resume.html) | `abstract fun resume(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [stepInto](step-into.html) | `abstract fun stepInto(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [stepOver](step-over.html) | `abstract fun stepOver(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [stepReturn](step-return.html) | `abstract fun stepReturn(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [suspend](suspend.html) | `abstract fun suspend(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |
| [terminate](terminate.html) | `abstract fun terminate(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>{@inheritDoc} |

