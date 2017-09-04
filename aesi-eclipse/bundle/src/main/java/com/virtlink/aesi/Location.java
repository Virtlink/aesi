package com.virtlink.aesi;

/**
 * A location in a document.
 */
public final class Location implements Comparable<Location> {
	
	private final int line;
	private final int character;
	
	/**
	 * Gets the line number.
	 * 
	 * @return The zero-based line number.
	 */
	public int getLine() { return this.line; }
	
	/**
	 * Gets the character offset on the line.
	 * 
	 * @return The zero-based character offset.
	 */
	public int getCharacter() { return this.character; }
	
	/**
	 * Creates a new instance of the {@link Location} class.
	 * 
	 * @param line The zero-based line number.
	 * @param character The zero-based character offset.
	 */
	public Location(int line, int character) {
		if (line < 0)
			throw new IllegalArgumentException("line must be positive or zero.");
		if (character < 0)
			throw new IllegalArgumentException("character must be positive or zero.");
		
		this.line = line;
		this.character = character;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Location
			&& equals((Location)obj);
	}
	
	/**
	 * Compares this object and the specified object for equality.
	 * 
	 * @param other The object to compare to.
	 * @return True when this object is equal to the specified object;
	 * otherwise, false.
	 */
	public boolean equals(Location other) {
		return other != null
			&& this.line == other.line
			&& this.character == other.character;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 17;
        result = 31 * result + Integer.hashCode(this.line);
        result = 31 * result + Integer.hashCode(this.character);
        return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Location other) {
		int comparison = 0;
		if (comparison == 0)
			comparison = Integer.compare(this.line, other.line);
		if (comparison == 0)
			comparison = Integer.compare(this.character, other.character);
		return comparison;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + this.line + ":" + this.character + ")";
	}

}