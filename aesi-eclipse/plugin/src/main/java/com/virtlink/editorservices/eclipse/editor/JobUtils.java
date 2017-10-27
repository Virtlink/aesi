package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;

/**
 * Jib utility functions.
 */
public final class JobUtils {
	private JobUtils() {}
	
	/**
	 * Creates a new scheduling rule of the specified rules.
	 * 
	 * @param rules The rules.
	 * @return The combined scheduling rule.
	 */
	public static ISchedulingRule ruleOf(ISchedulingRule... rules) {
		return MultiRule.combine(rules);
		// TODO: I don't know how this startupReadLock in Metaborg/Spoofax works.
//		return MultiRule.combine(globalRules.startupReadLock(), MultiRule.combine(rules));
	}
	
}
