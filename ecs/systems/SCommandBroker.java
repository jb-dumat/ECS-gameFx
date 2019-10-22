public class SCommandBroker {
    public static void putCommand(Command command) {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((e) -> {
                    CCommandList cmdList = (CCommandList) ecs.getComponent(e, CCommandList.class);

                    cmdList.commandList.add(command);
                },
                CCommandList.class);
    }

    public static void update() {
        EcsManager ecs = EcsManager.getInstance();



        ecs.forEachIfContains((e) -> {
            CCommandList cmdList = (CCommandList) ecs.getComponent(e, CCommandList.class);

            for (Command command : cmdList.commandList) {
                command.execute();
            }

            cmdList.commandList.clear();
        },
                CCommandList.class);
    }
}
