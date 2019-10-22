import java.util.LinkedList;
import java.util.Queue;

public class SCommandTake implements Command {
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

                    if (commandInput.command == null || !commandInput.command.equals("take"))
                        return;

                    if (commandInput.args == null || commandInput.args.size() < 1) {
                        toAdd.add(new COutDialog("Take what ?"));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    String itemName = commandInput.args.elementAt(0);
                    Entity item = ecs.getEntityByValue(CName.class, "name", itemName);
                    CInventory roomInventory = (CInventory) ecs.getComponent(roomEntity, CInventory.class);

                    if (!roomInventory.inventory.contains(item)) {
                        toAdd.add(new COutDialog("No " + itemName + " in the room."));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    CWeight itemWeight = (CWeight) ecs.getComponent(item, CWeight.class);
                    CStorageCapacity storageCapacity = (CStorageCapacity) ecs.getComponent(e, CStorageCapacity.class);
                    CInventory playerInventory = (CInventory) ecs.getComponent(e, CInventory.class);
                    CPlayerWeight playerWeight = (CPlayerWeight) ecs.getComponent(e, CPlayerWeight.class);
                    if (itemWeight == null || storageCapacity == null || playerInventory == null) {
                        ecs.remove(e, commandInput);
                        return;
                    }

                    if (playerWeight.currentWeight + itemWeight.weight >= storageCapacity.storageCapacity) {
                        toAdd.add(new COutDialog(itemName + " is too heavy."));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    roomInventory.inventory.remove(item);
                    playerInventory.inventory.add(item);
                    playerWeight.currentWeight += itemWeight.weight;

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
