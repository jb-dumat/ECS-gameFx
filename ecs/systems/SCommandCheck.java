public class SCommandCheck {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        CommandPool cmdPool = CommandPool.getInstance();

        ecs.forEachWith((e) -> {
            CCommandInput commandInput = (CCommandInput) ecs.getComponent(e, CCommandInput.class);

            if (!cmdPool.contains(commandInput.command)) {
                ecs.put(new Entity(), new COutDialog("I don't know what you mean"));
                commandInput.command = null;
                commandInput.args.clear();
            }
        },
                CPositionString.class
        );
    }
}
