package org.geepawhill.jltk.script;

public class ScriptLocation {
    public final String fileName;
    public final int lineNumber;

    public ScriptLocation() {
        /*
         This is a clever hack for finding out which script file and line
         failed when a ScriptAction fails.

         The 0th item in the stack is this constructor.
         The 1st item in the stack is the constructor of the ScriptAction.
         The 2nd item in the stack is the is the ScriptBuilder's API call.
         The 3rd item in the stack is the actual line of the script.
         */

        StackTraceElement caller = new Throwable().getStackTrace()[3];
        this.fileName = caller.getFileName();
        this.lineNumber = caller.getLineNumber();
    }
}
