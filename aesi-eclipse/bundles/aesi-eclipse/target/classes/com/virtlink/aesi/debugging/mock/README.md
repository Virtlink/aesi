# Mock Debugger
The mock debugger 'executes' a text file, showcasing the capabilities of the debugger.

For every line in a file, it will:

1.  Suspend if one of these conditions is met:

    - Execution suspended
    - Step completed
    - Breakpoint hit

2.  Execute the line.
3.  Suspend if one of these conditions is met:

    - Exception thrown

Executing a line can perform the following actions:

1.  Throw an exception, when the word `exception` occurs on the line.
2.  Jump to a different file, when the name of the file occurs on the line.
3.  Do nothing.
    
