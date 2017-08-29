---
title: IDebugSession.getScopes - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [getScopes](.)

# getScopes

`abstract fun getScopes(thread: IAesiThread, stackFrame: IAesiStackFrame): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IScope>`

Gets the scopes for the specified stack frame. Even when an implementation doesn't support scopes, they must at least return one (dummy) scope. This method is only called while execution is suspended.

### Parameters

`thread` - The thread.

`stackFrame` - The stack frame.

**Return**
A list of stack frames.

