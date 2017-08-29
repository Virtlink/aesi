---
title: IDebugSession.getChildVariables - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [getChildVariables](.)

# getChildVariables

`abstract fun getChildVariables(thread: IAesiThread, stackFrame: IAesiStackFrame, scope: IScope, variable: IVariable): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IVariable>`

Gets the child variables for the variable. This method is only called while execution is suspended.

### Parameters

`thread` - The thread.

`stackFrame` - The stack frame.

`scope` - The scope.

`variable` - The variable.

**Return**
A list of variables.

