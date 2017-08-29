---
title: org.slf4j.impl - aesi-eclipse
---

[aesi-eclipse](../index.html) / [org.slf4j.impl](.)

## Package org.slf4j.impl

### Types

| [ConsoleLoggerAdapter](-console-logger-adapter/index.html) | `class ConsoleLoggerAdapter : `[`LoggerBase`](-logger-base/index.html)`, `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)<br>Logs to the console view. |
| [EclipseLoggerAdapter](-eclipse-logger-adapter/index.html) | `class EclipseLoggerAdapter : `[`LoggerBase`](-logger-base/index.html)`, `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)<br>Logs to the Eclipse Error view. |
| [EclipseLoggerFactory](-eclipse-logger-factory/index.html) | `open class EclipseLoggerFactory : ILoggerFactory`<br>Creates Eclipse logger adapters. |
| [LoggerBase](-logger-base/index.html) | `abstract class LoggerBase : MarkerIgnoringBase`<br>Base implementation for loggers. |
| [StaticLoggerBinder](-static-logger-binder/index.html) | `class StaticLoggerBinder : LoggerFactoryBinder`<br>The binding of LoggerFactory class with an actual instance of ILoggerFactory is performed using information returned by this class. |

