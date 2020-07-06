package components;

import core.Command;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The component CCommandList contains a list of Command which
 * are used by the Broker System (Command Pattern)
 */
public class CCommandQueue implements IComponent {
    public CCommandQueue() {
        this.commandQueue = new LinkedList<>();
    }

    public Queue<Command> commandQueue;
}
