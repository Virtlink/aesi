package com.virtlink.paplj.intellij;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.intellij.openapi.diagnostic.Logger;

public final class PapljPlugin {
    private static final Logger logger = Logger.getInstance(PapljPlugin.class);

    // The shared instance
    private static PapljPlugin plugin = new PapljPlugin();

    // The dependency injector.
    private static Injector injector;

    /**
     * Gets the dependency injector.
     * @return The dependency injector.
     */
    public static Injector getInjector() {
        if (PapljPlugin.injector == null)
            throw new IllegalStateException("Guice injector is not initialized.");
        return PapljPlugin.injector;
    }

    /**
     * Returns the shared instance.
     * @return The shared instance.
     */
    public static PapljPlugin getDefault() {
        return PapljPlugin.plugin;
    }

    private PapljPlugin() {
        logger.info("PAPLJ plugin created.");
    }

    public void start() {
        logger.debug("Starting PAPLJ plugin.");
        PapljPlugin.injector = Guice.createInjector(new PapljIntellijModule());
        logger.info("PAPLJ plugin started.");
    }

}
