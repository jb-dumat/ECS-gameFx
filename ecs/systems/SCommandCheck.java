import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;

public class SCommandCheck {
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachWith((e) -> {
            CCommandInput commandInput = (CCommandInput) ecs.getComponent(e, CCommandInput.class);

            ecs.forEachWith((s) -> {
                CCommandSchemes schemes = (CCommandSchemes) ecs.getComponent(s, CCommandSchemes.class);

                if (schemes == null || commandInput == null || !schemes.schemes.containsKey(commandInput.command)) {
                    toAdd.add(new COutDialog("I don't know what you mean"));
//                    ecs.put(new Entity(), new COutDialog("I don't know what you mean"));
                    ecs.remove(e, commandInput);

                } else {
                    try {
                        SCommandBroker.putCommand(schemes.schemes.get(commandInput.command).newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                }

            }, CCommandSchemes.class);
        },
                CPositionString.class
        );

        toAdd.forEach(c -> { ecs.put(new Entity(), c); });
    }
}
