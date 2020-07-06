package systems;

import core.*;
import ecs.*;
import components.*;

/**
 * the SCommandBroker provides a way to manage
 * all the inouts commands.
 */
public class SCommandBroker {
    /**
     * Add a command to the executor list
     * @param command
     */
    public static void putCommand(Command command) {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((e) -> {
                    CCommandQueue cmdList = ecs.getComponent(e, CCommandQueue.class);

                    if (cmdList != null && cmdList.commandQueue != null)
                        cmdList.commandQueue.add(command);
                    },
                CCommandQueue.class
        );
    }

    /**
     * Executes all commands
     */
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((e) -> {
            CCommandQueue cmdList = ecs.getComponent(e, CCommandQueue.class);

            if (cmdList != null && cmdList.commandQueue != null) {
                cmdList.commandQueue.stream().forEach(c -> c.execute());
                cmdList.commandQueue.clear();
            }
        },
                CCommandQueue.class
        );
    }
}
