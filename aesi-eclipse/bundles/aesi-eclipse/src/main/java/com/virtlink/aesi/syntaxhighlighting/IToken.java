package com.virtlink.aesi.syntaxhighlighting;

import com.virtlink.aesi.Span;
import com.virtlink.aesi.Span2;

/**
 * A colorizer token.
 */
public interface IToken {
	/**
	 * Gets the location of the token relative to the start of the text.
	 * 
	 * @return The start and end of the token.
	 */
	Span getLocation();
	
	/**
	 * Gets the scope of the token.
	 * 
	 * The token's scope determines how the token is colored.
	 * 
	 * @return The name of the scope.
	 */
	String getScope();
}
