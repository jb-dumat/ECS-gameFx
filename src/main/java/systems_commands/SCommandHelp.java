package systems_commands;

import core.*;
import ecs.*;
import components.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The help command system handler
 * Lists all command
 */
public class SCommandHelp implements Command {
    @Override
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains((e) -> {
            CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);

            if (commandInput.command.equals("help")) {
                toAdd.add(new COutDialog(helpMessage.toString()));
            }
            
            ecs.remove(e, commandInput);
        },
                CCommandInput.class);

        toAdd.forEach(c -> ecs.put(new Entity(), c));
    }

    public static StringBuilder helpMessage;
}
