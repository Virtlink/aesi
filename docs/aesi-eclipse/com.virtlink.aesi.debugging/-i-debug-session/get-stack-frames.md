---
title: IDebugSession.getStackFrames - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.debugging](../index.html) / [IDebugSession](index.html) / [getStackFrames](.)

# getStackFrames

`abstract fun getStackFrames(thread: IAesiThread): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<IAesiStackFrame>`

Gets the stack frames for the specified thread. Even when an implementation doesn't support stack frames, they must at least return one (dummy) stack frame. This method is only called while execution is suspended.

### Parameters

`thread` - The thread.

**Return**
A list of stack frames.

