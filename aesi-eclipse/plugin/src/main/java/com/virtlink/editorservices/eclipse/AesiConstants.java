package com.virtlink.editorservices.eclipse;

import com.google.inject.Inject;

/**
 * Constants.
 */
public final class AesiConstants {

	private AesiConstants() {}
	
	@Inject @FileExtension private static String fileExtension;
	
	/**
	 * Gets the file exension for the language.
	 *  
	 * @return The file extension.
	 */
	public static String getFileExtension() {
		return fileExtension;
	}
	
}
