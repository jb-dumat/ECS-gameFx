import java.util.ArrayList;
import java.util.List;

public class SDialogOutput {
    public static void compute() {
        EcsManager ecs = EcsManager.getInstance();

        MultiMap<Entity, IComponent> commands = ecs.getComponentsPool().get("COutDialog");
        List<Entity> toRemove = new ArrayList<>();

        for (Entity entityCommand : commands.keySet()) {
            String dialogText = ((COutDialog)commands.get(entityCommand).toArray()[0]).text;

            if (dialogText != null) {
                System.out.println(dialogText);
                toRemove.add(entityCommand);
            }
        }
        toRemove.forEach(e -> commands.remove(e));
    }
}
