---
title: Location - aesi-java
---

[aesi-java](../../index.html) / [com.virtlink.editorservices](../index.html) / [Location](.)

# Location

`data class Location : `[`Comparable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable/index.html)`<Location>`

A location in a document.

### Constructors

| [&lt;init&gt;](-init-.html) | `Location(line: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, character: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)`<br>A location in a document. |

### Properties

| [character](character.html) | `val character: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>The zero-based character offset of the location. |
| [line](line.html) | `val line: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>The zero-based line number of the location. |

### Functions

| [compareTo](compare-to.html) | `fun compareTo(other: Location): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.html) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

