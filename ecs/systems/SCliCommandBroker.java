/**
 * the SCommandBroker provides a way to manage
 * all the inouts commands.
 */
public class SCliCommandBroker {
    /**
     * Add a command to the executor list
     * @param command
     */
    public static void putCommand(Command command) {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((e) -> {
                    CCommandList cmdList = ecs.getComponent(e, CCommandList.class);

                    if (cmdList != null && cmdList.commandList != null)
                        cmdList.commandList.add(command);
                    },
                CCommandList.class
        );
    }

    /**
     * Executes all commands
     */
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((e) -> {
            CCommandList cmdList = ecs.getComponent(e, CCommandList.class);

            if (cmdList != null && cmdList.commandList != null) {
                for (Command command : cmdList.commandList) {
                    command.execute();
                }
                cmdList.commandList.clear();
            }
        },
                CCommandList.class
        );
    }
}
