package com.virtlink.aesi;

/**
 * A location in a document.
 */
public final class Offset implements Comparable<Offset> {

	private final int value;

	/**
	 * Gets the character offset.
	 *
	 * @return The zero-based character offset.
	 */
	public int getValue() { return this.value; }

	/**
	 * Creates a new instance of the {@link Offset} class.
	 *
	 * @param value The zero-based character offset.
	 */
	public Offset(int value) {
		if (value < 0)
			throw new IllegalArgumentException("value must be positive or zero.");

		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Offset
			&& equals((Offset)obj);
	}
	
	/**
	 * Compares this object and the specified object for equality.
	 * 
	 * @param other The object to compare to.
	 * @return True when this object is equal to the specified object;
	 * otherwise, false.
	 */
	public boolean equals(Offset other) {
		return other != null
			&& this.value == other.value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 17;
        result = 31 * result + Integer.hashCode(this.value);
        return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Offset other) {
		int comparison = 0;
		if (comparison == 0)
			comparison = Integer.compare(this.value, other.value);
		return comparison;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "" + this.value;
	}

}