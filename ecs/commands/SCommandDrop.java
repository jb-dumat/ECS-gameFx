import java.util.LinkedList;
import java.util.Queue;

public class SCommandDrop implements Command {
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains(e -> {

                    CCommandInput commandInput = (CCommandInput) ecs.getComponent(e, CCommandInput.class);
                    CPositionString positionString = (CPositionString) ecs.getComponent(e, CPositionString.class);

                    if (commandInput == null || positionString == null)
                        return;

                    Entity roomEntity = ecs.getEntityByValue(CName.class, "name", positionString.roomTag);
                    if (roomEntity == null)
                        return;

                    if (commandInput.command == null || !commandInput.command.equals("drop"))
                        return;

                    if (commandInput.args == null || commandInput.args.size() < 1) {
                        toAdd.add(new COutDialog("Drop what ?"));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    String itemName = commandInput.args.elementAt(0);
                    Entity item = ecs.getEntityByValue(CName.class, "name", itemName);
                    CInventory playerInventory = (CInventory) ecs.getComponent(e, CInventory.class);

                    if (!playerInventory.inventory.contains(item)) {
                        toAdd.add(new COutDialog("You don't have the " + itemName + "."));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    CWeight itemWeight = (CWeight) ecs.getComponent(item, CWeight.class);
                    CInventory roomInventory = (CInventory) ecs.getComponent(roomEntity, CInventory.class);
                    CPlayerWeight playerWeight = (CPlayerWeight) ecs.getComponent(e, CPlayerWeight.class);
                    if (itemWeight == null || roomInventory == null || playerWeight == null) {
                        ecs.remove(e, commandInput);
                        return;
                    }

                    playerInventory.inventory.remove(item);
                    roomInventory.inventory.add(item);
                    playerWeight.currentWeight -= itemWeight.weight;

                    ecs.remove(e, commandInput);
                },

                CCommandInput.class,
                CPositionString.class,
                CInventory.class,
                CStorageCapacity.class
        );

        toAdd.forEach(c -> ecs.put(new Entity(), c));
    }
}
