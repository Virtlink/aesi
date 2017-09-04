package com.virtlink.aesi;

/**
 * A span in a document.
 */
public final class Span2 {
	
	private final Location start;
	private final Location end;
	
	/**
	 * Gets the start location of the span.
	 * 
	 * @return The location of the first character in the span.
	 */
	public Location getStart() { return this.start; }
	
	/**
	 * Gets the end location of the span.
	 * 
	 * @return THe location of the first character after the span.
	 */
	public Location getEnd() { return this.end; }

	/**
	 * Creates a new instance of the {@link Span2} class.
	 * 
	 * @param start The start location.
	 * @param end The end location.
	 */
	public Span2(Location start, Location end) {
		if (start == null)
			throw new IllegalArgumentException("start must not be null.");
		if (end == null)
			throw new IllegalArgumentException("end must not be null.");
		
		this.start = start;
		this.end = end;
	}

	/**
	 * Creates a new instance of the {@link Span2} class.
	 * 
	 * @param startLine The zero-based start line number.
	 * @param startCharacter The zero-based start character offset.
	 * @param endLine The zero-based end line number.
	 * @param endCharacter The zero-based end character offset.
	 */
	public Span2(int startLine, int startCharacter, int endLine, int endCharacter) {
		this(new Location(startLine, startCharacter), new Location(endLine, endCharacter));
		// Nothing to do.
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Span2
			&& equals((Span2)obj);
	}
	
	/**
	 * Compares this object and the specified object for equality.
	 * 
	 * @param other The object to compare to.
	 * @return True when this object is equal to the specified object;
	 * otherwise, false.
	 */
	public boolean equals(Span2 other) {
		return other != null
			&& this.start.equals(other.start)
			&& this.end.equals(other.end);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 17;
        result = 31 * result + this.start.hashCode();
        result = 31 * result + this.end.hashCode();
        return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + this.start.getLine() + ":" + this.start.getCharacter() + "-" + this.end.getLine() + ":" + this.end.getCharacter() + ")";
	}
}
