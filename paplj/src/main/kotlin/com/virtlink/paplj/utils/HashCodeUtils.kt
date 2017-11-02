package com.virtlink.paplj.utils

/**
 * Utility functions for calculating hash codes.
 */
object HashCodeUtils {

    /**
     * Calculates the hash code of the specified sequence of values.
     *
     * The order of the values matters.
     *
     * @param values The values.
     * @return The calculated hash code.
     */
    fun calculateHashCode(vararg values: Any?): Int
            = calculateHashCode(values.asIterable())

    /**
     * Calculates the hash code of the specified sequence of values.
     *
     * The order of the values matters.
     *
     * @param values The values.
     * @return The calculated hash code.
     */
    fun calculateHashCode(values: Iterable<Any?>): Int {
        val initPrime = 17  // See: https://stackoverflow.com/a/263416/146622
        val prime = 92821   // See: https://stackoverflow.com/a/2816747/146622
        return values.fold(initPrime, { i, t -> i * prime + (t?.hashCode() ?: 0)})
    }

}