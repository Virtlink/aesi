package com.virtlink.aesi.eclipse;

import org.eclipse.jface.text.BadLocationException;

import com.virtlink.aesi.Location;

public class AesiUtils {

	/**
	 * Converts a document offset into a {@link Location}.
	 * 
	 * @param document The document.
	 * @param offset The offset.
	 * @return The {@link Location}.
	 */
	public static Location offsetToLocation(org.eclipse.jface.text.IDocument document, int offset) {
		try {
			int line = document.getLineOfOffset(offset);
			int character = offset - document.getLineOffset(line);
			return new Location(line, character);
		} catch (BadLocationException e) {
			throw new RuntimeException("Unhandled exception.", e);
		}
	}
	
	public static int locationToOffset(Location location, org.eclipse.jface.text.IDocument document) {
		try {
		return document.getLineOffset(location.getLine()) + location.getCharacter();
		} catch (BadLocationException e) {
			return document.getLength();
//			throw new RuntimeException("Unhandled exception.", e);
		}
	}
}
