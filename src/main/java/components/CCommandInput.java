package components;

import java.util.Vector;

/**
 * The component CCommandInput holds a command and its arguments.
 */
public class CCommandInput implements IComponent {
    public CCommandInput(String command, Vector<String> args) {
        this.command = command;
        this.args = args;
    }

    public String command;
    public Vector<String> args;
 }
