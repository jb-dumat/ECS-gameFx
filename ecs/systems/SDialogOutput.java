import java.util.Iterator;
import java.util.Map;

public class SDialogOutput {
    public static void compute() {
        EcsManager ecs = EcsManager.getInstance();
//        Iterator<MultiMap<Entity, IComponent>> commandInputs = ecs.getComponentsPool().get("CDialog");

        MultiMap<Entity, IComponent> commands = ecs.getComponentsPool().get("CDialog");

        for (Entity entityCommand : commands.keySet()) {
            String dialogText = ((CDialog)commands.get(entityCommand).toArray()[0]).text;

            if (dialogText != null) {
                System.out.println(dialogText);
                commands.remove(entityCommand);
            }
        }
/*        while (commandInputs.hasNext()) {
            Map.Entry<Entity, IComponent> entry = commandInputs.next();
            String dialogText = ((CDialog)entry.getValue()).text;

            if (dialogText != null) {
                System.out.println(dialogText);
                commandInputs.remove();
            }
        }
*/    }
}
