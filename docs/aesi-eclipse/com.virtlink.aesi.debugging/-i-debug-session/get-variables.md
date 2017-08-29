---
title: IDebugSession.getVariables - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [getVariables](.)

# getVariables

`abstract fun getVariables(thread: IAesiThread, stackFrame: IAesiStackFrame, scope: IScope): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IVariable>`

Gets the variables for the specified scope. This method is only called while execution is suspended.

### Parameters

`thread` - The thread.

`stackFrame` - The stack frame.

`scope` - The scope.

**Return**
A list of variables.

