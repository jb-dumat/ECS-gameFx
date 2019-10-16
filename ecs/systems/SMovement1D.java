import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class SMovement1D {
    public static void compute() {
        EcsManager ecs = EcsManager.getInstance();

        MultiMap<Entity, IComponent> commandInputs = ecs.getComponentsPool().get("CCommandInput");
        MultiMap<Entity, IComponent> positions = ecs.getComponentsPool().get("CPosition1D");
        MultiMap<Entity, IComponent> exits = ecs.getComponentsPool().get("CExit");

        if (commandInputs == null || positions == null)
            return;

        for (Entity entityCommand : commandInputs.keySet())  {
            String commandTag = ((CCommandInput)commandInputs.get(entityCommand).toArray()[0]).command;
            Vector<String> args = ((CCommandInput)commandInputs.get(entityCommand).toArray()[0]).args;

            if (!commandTag.equals("go"))
                continue;

            String positionTag = ((CPosition1D)positions.get(entityCommand).toArray()[0]).roomTag;
            boolean commandExecuted = false;

            for (Entity exitEntity : exits.keySet()) {
                String roomTag = ((CExit)exits.get(exitEntity).toArray()[0]).roomTag;
                String exitTag = ((CExit)exits.get(exitEntity).toArray()[0]).exitTag;
                String directionTag = ((CExit)exits.get(exitEntity).toArray()[0]).directionTag;

                System.out.println(args.elementAt(0));
                if (roomTag.equals(positionTag) && args.elementAt(0).equals(directionTag)) {
                    ((CPosition1D)positions.get(entityCommand).toArray()[0]).roomTag = exitTag;
                    commandExecuted = true;
                    ecs.attachComponent(new Entity(), new CDialog("You've gone " + directionTag));
                }
            }

            if (!commandExecuted)
                ecs.attachComponent(new Entity(), new CDialog("Impossible to reach " + args.elementAt(0)));

            commandInputs.remove(entityCommand);
        }
    }

}
