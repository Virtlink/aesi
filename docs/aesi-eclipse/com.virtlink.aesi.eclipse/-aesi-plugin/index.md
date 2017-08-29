---
title: AesiPlugin - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.eclipse](../index.html) / [AesiPlugin](.)

# AesiPlugin

`open class AesiPlugin : AbstractUIPlugin`

The activator class controls the plug-in life cycle

### Constructors

| [&lt;init&gt;](-init-.html) | `AesiPlugin()`<br>The constructor |

### Properties

| [PLUGIN_ID](-p-l-u-g-i-n_-i-d.html) | `static val PLUGIN_ID: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Functions

| [getDebugModelIdentifier](get-debug-model-identifier.html) | `open static fun getDebugModelIdentifier(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getDefault](get-default.html) | `open static fun getDefault(): AesiPlugin?`<br>Returns the shared instance |
| [getImageDescriptor](get-image-descriptor.html) | `open static fun getImageDescriptor(path: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): ImageDescriptor`<br>Returns an image descriptor for the image file at the given plug-in relative path |
| [start](start.html) | `open fun start(context: BundleContext): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [stop](stop.html) | `open fun stop(context: BundleContext): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

