---
title: StaticLoggerBinder - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [org.slf4j.impl](../index.html) / [StaticLoggerBinder](.)

# StaticLoggerBinder

`class StaticLoggerBinder : LoggerFactoryBinder`

The binding of LoggerFactory class with an actual instance of ILoggerFactory is performed using information returned by this class.

**Author**
Ceki Gülcü

### Properties

| [REQUESTED_API_VERSION](-r-e-q-u-e-s-t-e-d_-a-p-i_-v-e-r-s-i-o-n.html) | `static var REQUESTED_API_VERSION: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Declare the version of the SLF4J API this implementation is compiled against. The value of this field is modified with each major release. |

### Functions

| [getLoggerFactory](get-logger-factory.html) | `fun getLoggerFactory(): ILoggerFactory` |
| [getLoggerFactoryClassStr](get-logger-factory-class-str.html) | `fun getLoggerFactoryClassStr(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getSingleton](get-singleton.html) | `static fun getSingleton(): StaticLoggerBinder`<br>Return the singleton of this class. |

