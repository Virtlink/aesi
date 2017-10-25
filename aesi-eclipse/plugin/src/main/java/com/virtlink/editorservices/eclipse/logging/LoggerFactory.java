package com.virtlink.editorservices.eclipse.logging;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.impl.EclipseLoggerFactory;

public final class LoggerFactory {

    private static final ILoggerFactory loggerFactory = new EclipseLoggerFactory();

    private LoggerFactory() {}

    public static Logger getLogger(String name) {
        return loggerFactory.getLogger(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
