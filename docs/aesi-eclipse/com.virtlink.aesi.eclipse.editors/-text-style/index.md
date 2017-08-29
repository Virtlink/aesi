---
title: TextStyle - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.eclipse.editors](../index.html) / [TextStyle](.)

# TextStyle

`class TextStyle`

Describes the style of source code.

### Constructors

| [&lt;init&gt;](-init-.html) | `TextStyle(color: RGB, bold: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, italic: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, underlined: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, strickenthrough: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`)`<br>`TextStyle(color: RGB)`<br>Creates a new instance of the TextStyle class. |

### Functions

| [createTextAttribute](create-text-attribute.html) | `fun createTextAttribute(colorManager: ColorManager): TextAttribute`<br>Creates a text attribute for this style. |
| [getColor](get-color.html) | `fun getColor(): RGB`<br>Gets the color of the text. |
| [isBold](is-bold.html) | `fun isBold(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Gets whether the text is bold. |
| [isItalic](is-italic.html) | `fun isItalic(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Gets whether the text is italic. |
| [isStrikenthrough](is-strikenthrough.html) | `fun isStrikenthrough(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Gets whether the text is strickenthrough. |
| [isUnderlined](is-underlined.html) | `fun isUnderlined(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Gets whether the text is underlined. |

