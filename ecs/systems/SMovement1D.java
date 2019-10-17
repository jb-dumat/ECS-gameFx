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

            if (args.size() < 1) {
                ecs.attachComponent(new Entity(), new COutDialog("go where ?"));
                commandInputs.remove(entityCommand);
                continue;
            }

            String positionTag = ((CPosition1D)positions.get(entityCommand).toArray()[0]).roomTag;
            boolean commandExecuted = false;

            for (Entity exitEntity : exits.keySet()) {

                for (int i = 0; i < exits.get(exitEntity).size(); i++) {

                    String roomTag = ((CExit) exits.get(exitEntity).toArray()[i]).roomTag;
                    String exitTag = ((CExit) exits.get(exitEntity).toArray()[i]).exitTag;
                    String directionTag = ((CExit) exits.get(exitEntity).toArray()[i]).directionTag;
                    Entity exitID = ((CExit) exits.get(exitEntity).toArray()[i]).exitID;

                    if (roomTag.equals(positionTag) && args.elementAt(0).equals(directionTag)) {
                        ((CPosition1D) positions.get(entityCommand).toArray()[0]).roomTag = exitTag;

                        commandExecuted = true;

                        String exitDesc = ((CDescription) ecs.getComponentsPool().get("CDescription").get(exitID).toArray()[0]).description;

                        ecs.attachComponent(new Entity(), new COutDialog("You are " + exitDesc));

                        String msg = new String("Exits: ");
                        for (int it = 0; it < exits.get(exitEntity).size(); it++) {
                            msg += ((CExit) exits.get(exitEntity).toArray()[it]).directionTag + " ";
                        }
                        ecs.attachComponent(new Entity(), new COutDialog(msg));
                    }
                }

            }

            if (!commandExecuted)
                ecs.attachComponent(new Entity(), new COutDialog("There is no door!"));

            commandInputs.remove(entityCommand);
        }
    }

}
