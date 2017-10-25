package com.virtlink.aesi.eclipse;

import com.virtlink.editorservices.Position;
import org.eclipse.jface.text.BadLocationException;

public class AesiUtils {

	/**
	 * Converts a document offset into a {@link Position}.
	 * 
	 * @param document The document.
	 * @param offset The offset.
	 * @return The {@link Position}.
	 */
	public static Position offsetToLocation(org.eclipse.jface.text.IDocument document, int offset) {
		try {
			int line = document.getLineOfOffset(offset);
			int character = offset - document.getLineOffset(line);
			return new Position(line, character);
		} catch (BadLocationException e) {
			throw new RuntimeException("Unhandled exception.", e);
		}
	}
	
	public static int locationToOffset(Position position, org.eclipse.jface.text.IDocument document) {
		try {
		return document.getLineOffset(position.getLine()) + position.getCharacter();
		} catch (BadLocationException e) {
			return document.getLength();
		}
	}
}
