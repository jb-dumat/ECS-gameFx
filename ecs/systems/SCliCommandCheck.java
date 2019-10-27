import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;

public class SCliCommandCheck {

    /**
     * Check if the input command holded by the entity e is valid.
     * @param e Entity which holds the command
     * @param commandInput The input command to check
     * @param toAdd A queue to manage concurrent modifications upon the ecs multimap.
     */
    private static void checkCommandSchemes(Entity e, CCommandInput commandInput, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((s) -> {
                    CCommandSchemes schemes = ecs.getComponent(s, CCommandSchemes.class);

                    if (schemes == null || !schemes.schemes.containsKey(commandInput.command)) {
                        toAdd.add(new COutDialog("I don't know what you mean"));
                        ecs.remove(e, commandInput);
                    } else {
                        try {
                            SCliCommandBroker.putCommand(schemes.schemes.get(commandInput.command).newInstance());
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                CCommandSchemes.class
        );
    }

    /**
     * CommandCheck System.
     * Iterate on all CCommandInput and verify they are
     * a valid command scheme.
     */
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains((e) -> {
                    CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);
                    if (commandInput == null)
                        return;

                    checkCommandSchemes(e, commandInput, toAdd);
                },
                CCommandInput.class
        );

        toAdd.forEach(c -> { ecs.put(new Entity(), c); });
    }
}
