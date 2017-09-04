package com.virtlink.aesi.syntaxhighlighting;

import com.virtlink.aesi.Span;
import com.virtlink.aesi.Span2;

/**
 * A colorizer token.
 */
public class Token implements IToken {
	
	private final Span location;
	private final String scope;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Span getLocation() { return this.location; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getScope() { return this.scope; }

	/**
	 * Creates a new instance of the {@link Token} class.
	 * 
	 * @param location The token's location.
	 * @param scope The token's scope name.
	 */
	public Token(Span location, String scope) {
		if (location == null)
			throw new IllegalArgumentException("location must not be null.");
		if (scope == null)
			throw new IllegalArgumentException("scope must not be null.");
		
		this.location = location;
		this.scope = scope;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Token
			&& equals((Token)obj);
	}
	
	/**
	 * Compares this object and the specified object for equality.
	 * 
	 * @param other The object to compare to.
	 * @return True when this object is equal to the specified object;
	 * otherwise, false.
	 */
	public boolean equals(Token other) {
		return other != null
			&& this.location.equals(other.location)
			&& this.scope.equals(other.scope);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = 17;
        result = 31 * result + this.location.hashCode();
        result = 31 * result + this.scope.hashCode();
        return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.scope.toString() + "@" + this.location.toString();
	}
}
