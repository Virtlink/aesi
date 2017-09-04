package com.virtlink.aesi;

/**
 * A span in a document.
 */
public final class Span {

	private final int offset;
	private final int length;

	/**
	 * Gets the inclusive start offset of the span.
	 *
	 * @return The zero-based offset of the first character in the span.
	 */
	public int getStartOffset() { return this.offset; }

	/**
	 * Gets the exclusive end offset of the span.
	 *
	 * @return The zero-based offset of the first character after the span.
	 */
	public int getEndOffset() { return this.offset + this.length; }

	/**
	 * Gets the length of the span.
	 * @return The number of characters in the span.
	 */
	public int getLength() { return this.length; }

	/**
	 * Gets whether the span is empty.
	 * @return True when the span is empty; otherwise, false.
	 */
	public boolean isEmpty() { return this.length == 0; }

	/**
	 * Creates a new instance of the {@link Span} class.
	 *
	 * @param offset The zero-based start character offset.
	 * @param length The number of characters in the span.
	 */
	private Span(int offset, int length) {
		if (offset < 0)
			throw new IllegalArgumentException("offset must be positive or zero.");
		if (length < 0)
			throw new IllegalArgumentException("length must be positive or zero.");

		this.offset = offset;
		this.length = length;
	}

	/**
	 * Creates a new instance of the {@link Span} class
	 * with the specified start offset and length.
	 *
	 * @param offset The zero-based start character offset.
	 * @param length The number of characters in the span.
	 * @return The resulting {@link Span} object.
	 */
	public static Span of(int offset, int length) {
		if (offset < 0)
			throw new IllegalArgumentException("offset must be positive or zero.");
		if (length < 0)
			throw new IllegalArgumentException("length must be positive or zero.");

		return new Span(offset, length);
	}

	/**
	 * Creates a new instance of the {@link Span} class
	 * with the specified start and end offsets.
	 *
	 * @param startOffset The zero-based start character offset.
	 * @param endOffset The zero-based end character offset.
	 * @return The resulting {@link Span} object.
	 */
	public static Span between(int startOffset, int endOffset) {
		if (startOffset < 0)
			throw new IllegalArgumentException("startOffset must be positive or zero.");
		if (endOffset < startOffset)
			throw new IllegalArgumentException("endOffset must be greater than or equal to startOffset.");

		return new Span(startOffset, endOffset - startOffset);
	}

	/**
	 * Determines whether the specified offset lies within this span.
	 *
	 * An empty span contains no offsets.
	 *
	 * @param offset The offset to test.
	 * @return True when the offset lies within this span; otherwise, false.
	 */
	public boolean contains(int offset) {
		return this.getStartOffset() <= offset && offset < this.getEndOffset();
	}

	/**
	 * Determines whether the specified span lies completely within this span.
	 *
	 * @param span The span to test.
	 * @return True when the specified span lies completely within this span; otherwise, false.
	 */
	public boolean contains(Span span) {
		if (span == null)
			throw new IllegalArgumentException("span must not be null.");

		return this.getStartOffset() <= span.getStartOffset()
			&& span.getEndOffset() <= this.getEndOffset();
	}

	/**
	 * Determines whether the specified span intersects with this span.
	 *
	 * @param span The span to test.
	 * @return True when the specified span intersects with this span; otherwise, false.
	 */
	public boolean intersects(Span span) {
		if (span == null)
			throw new IllegalArgumentException("span must not be null.");

		return this.contains(span.getStartOffset())
			|| this.contains(span.getEndOffset());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Span
			&& equals((Span)obj);
	}

	/**
	 * Compares this object and the specified object for equality.
	 *
	 * @param other The object to compare to.
	 * @return True when this object is equal to the specified object;
	 * otherwise, false.
	 */
	public boolean equals(Span other) {
		return other != null
			&& this.offset == other.offset
			&& this.length == other.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 17;
        result = 31 * result + Integer.hashCode(this.offset);
        result = 31 * result + Integer.hashCode(this.length);
        return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + this.getStartOffset() + "-" + this.getEndOffset() + ")";
	}
}
