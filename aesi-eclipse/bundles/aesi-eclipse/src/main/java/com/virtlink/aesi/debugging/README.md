# Debugger

A debugger provides the functionality to _debug_ a running program.

The debugger is in one of six states:

1.  **Loaded**<br />
    This is the state immediately after the debugger has been instantiated.
    In this state only the `initialize()` action is allowed.
2.  **Initialized**<br />
    The debugger has been initialized.
    In this state only the `attach()` and `launch()` actions are allowed.
3.  **Ready**<br />
    The debugger has an associated program.
    In this state the breakpoints can be configured,
    and whether to start the program running or suspended.
    In this state `start()` is allowed.
4.  **Running**<br />
    The program is running.
    In this state `suspend()` is allowed.
5.  **Suspended**<br />
    The program is suspended.
    In this state `resume()`, `step()`, `stepIn()`, `stepOut()` are allowed.
6.  **Done**<br />
    The associated program has been terminated or detached.
    In this state only `exit()` is allowed.
7.  **Exited**<br />
    The debugger is no longer running.
    (This state has no representation.)

    