package com.virtlink.paplj.intellij;

import com.google.inject.Guice;
import com.google.inject.Injector;

public final class PapljPlugin {
    // The shared instance
    private static PapljPlugin plugin = new PapljPlugin();

    // The dependency injector.
    private static Injector injector;

    /**
     * Gets the dependency injector.
     * @return The dependency injector.
     */
    public static Injector getInjector() { return PapljPlugin.injector; }

    /**
     * Returns the shared instance.
     * @return The shared instance.
     */
    public static PapljPlugin getDefault() {
        return PapljPlugin.plugin;
    }

    private PapljPlugin() {
    }

    public void start() {
        PapljPlugin.injector = Guice.createInjector(new PapljModule());
    }

}
