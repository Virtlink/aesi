---
title: IEvaluator.evaluate - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IEvaluator](index.html) / [evaluate](.)

# evaluate

`abstract fun evaluate(expression: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Nullable stackFrame: @Nullable IAesiStackFrame?): `[`IValue`](../-i-value.html)

Evaluates the specified expression.

### Parameters

`expression` - The expression to evaluate.

`stackFrame` - The stack frame that provides the scope for the expression; or `null` to evaluate the expression in the global scope.

**Return**
The result of evaluating the expression.

