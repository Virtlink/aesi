package com.virtlink.aesi.eclipse;

/**
 * Pre- and post-conditions.
 */
public final class Contract {

	/**
	 * Requires that the condition holds for the argument with the specified name.
	 * @param argumentName The argument name.
	 * @param condition The condition to check.
	 */
	public static void require(String argumentName, boolean condition) {
		if (!condition)
			throw new IllegalArgumentException("Condition failed for argument " + argumentName);
	}
	
	/**
	 * Requires that the object is not null.
	 * @param argumentName The argument name.
	 * @param obj The object to check.
	 */
	public static <T> void requireNotNull(String argumentName, T obj) {
		if (obj == null)
			throw new IllegalArgumentException("Unexpected null for argument " + argumentName);
	}
	
}
