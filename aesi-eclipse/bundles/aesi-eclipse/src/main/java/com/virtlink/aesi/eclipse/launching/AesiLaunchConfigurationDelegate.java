package com.virtlink.aesi.eclipse.launching;

import com.virtlink.aesi.debugging.mock.MockDebugSession;
import com.virtlink.aesi.debugging.mock.MockDebugger;
import com.virtlink.aesi.eclipse.debugging.model.AesiDebugTarget;
import com.virtlink.aesi.eclipse.logging.LoggerFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.slf4j.Logger;

import java.nio.file.Paths;
import java.util.Arrays;

/**
 * The launch configuration delegate.
 */
public class AesiLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

    private static final Logger log = LoggerFactory.getLogger(AesiLaunchConfigurationDelegate.class);

    /** {@inheritDoc} */
    @Override
    public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
        switch (mode) {
            case ILaunchManager.RUN_MODE:
                launchRun(configuration, launch, monitor);
                break;
            case ILaunchManager.DEBUG_MODE:
                launchDebug(configuration, launch, monitor);
                break;
            default:
                log.warn("Unsupported AESI launch mode " + mode);
                // Ignored.
                break;
        }
    }

    /**
     * Launches the program in run mode.
     *
     * @param configuration The launch configuration.
     * @param launch The launch information.
     * @param monitor The progress monitor.
     */
    private void launchRun(ILaunchConfiguration configuration, ILaunch launch, IProgressMonitor monitor) throws CoreException {
        log.info("Running AESI");
        // TODO
    }

    /**
     * Launches the program in debug mode.
     *
     * @param configuration The launch configuration.
     * @param launch The launch information.
     * @param monitor The progress monitor.
     */
    private void launchDebug(ILaunchConfiguration configuration, ILaunch launch, IProgressMonitor monitor) throws CoreException {
        log.info("Debugging AESI");
        MockDebugger debugger = new MockDebugger(
                Paths.get("/home/daniel/runtime-EclipseApplication/testproject/src/test.aesi"),
                Arrays.asList(
                        Paths.get("/home/daniel/runtime-EclipseApplication/testproject/src/test.aesi")
                )
        );
        IDebugTarget debugTarget = new AesiDebugTarget(launch, debugger);
        launch.addDebugTarget(debugTarget);
    }
}
