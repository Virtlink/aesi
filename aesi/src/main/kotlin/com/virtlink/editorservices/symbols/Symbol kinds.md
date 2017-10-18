# Symbol kinds

- `class`
- `trait`
- `interface`
- `function`
- `method`
- `constructor`
- `destructor`
- `package` / `namespace`
- `file`
- `object` / `singleton`
- `data class` / `struct`
- `enum`
- `attribute`
- `operator method`
- `field`
- `property`
- `variable`
- `constant`

# Attributes`

- `abstract` / `open`
- `const`
- `deprecated`
- `final` / `sealed`
- `readonly` / `val`
- `readwrite` / `var`
- `pure`
- `virtual`
- `static`
- `extension method`

- `public`
- `private`
- `internal`
- `package private`
- `protected`
- `protected and internal`
- `protected or internal`
- `friend`

## Example

    [Pure, Deprecated]
    protected internal static int Int.Inc(this int value) = value + 1;

Kinds and attributes:

- `protected or internal`
- `static`
- `pure`
- `extension method`
- `deprecated`
- `function`


