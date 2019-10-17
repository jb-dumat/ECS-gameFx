import java.util.Vector;

public class SCommandCheck {
    public static void compute() {
        EcsManager ecs = EcsManager.getInstance();
        CommandPool cmdPool = CommandPool.getInstance();

        if (cmdPool == null)
            return;

        MultiMap<Entity, IComponent> commandInputs = ecs.getComponentsPool().get("CCommandInput");
        if (commandInputs == null)
            return;

        for (Entity command : commandInputs.keySet()) {
            String commandTag = ((CCommandInput) commandInputs.get(command).toArray()[0]).command;
            Vector<String> args = ((CCommandInput) commandInputs.get(command).toArray()[0]).args;

            if (!cmdPool.contains(commandTag)) {
                ecs.removeEntity(command);
                ecs.attachComponent(new Entity(), new COutDialog("I don't know what you mean..."));
            }
        }
    }
}
