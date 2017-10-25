package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Creates Eclipse logger adapters.
 */
public class EclipseLoggerFactory implements ILoggerFactory {

    private final ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Logger getLogger(final String name) {
        final Logger slf4jLogger = this.loggerMap.get(name);
        if (slf4jLogger != null) {
            // Return the logger from the map.
            return slf4jLogger;
        } else {
            // Put the new logger in the map and return it,
            // unless another logger suddenly appeared in the map, which we then return instead.
            final Logger newInstance = new ConsoleLoggerAdapter(name);
//            final Logger newInstance = new EclipseLoggerAdapter(name);
            final Logger oldInstance = this.loggerMap.putIfAbsent(name, newInstance);
            return oldInstance != null ? oldInstance : newInstance;
        }
    }
}
