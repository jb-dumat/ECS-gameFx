import java.util.LinkedList;
import java.util.Queue;

public class SCommandGive implements Command {
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains(e -> {

                    CCommandInput commandInput = (CCommandInput) ecs.getComponent(e, CCommandInput.class);
                    CPositionString positionString = (CPositionString) ecs.getComponent(e, CPositionString.class);

                    if (commandInput == null || positionString == null) {
                        ecs.remove(e, commandInput);
                        return;
                    }

                    if (commandInput.command == null || !commandInput.command.equals("give")) {
                        return;
                    }

                    if (commandInput.args == null || commandInput.args.size() < 1) {
                        toAdd.add(new COutDialog("Give what ?"));
                        ecs.remove(e, commandInput);
                        return;
                    } else if (commandInput.args.size() < 2) {
                        toAdd.add(new COutDialog("Give to who ?"));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    Entity otherEntity = ecs.getEntityByValue(CName.class, "name", commandInput.args.elementAt(1));
                    if (otherEntity == null) {
                        toAdd.add(new COutDialog(commandInput.args.elementAt(1) + " doesn't exist."));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    if (otherEntity == e) {
                        toAdd.add(new COutDialog("You can't give something to yourself."));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    CPositionString otherPosition = (CPositionString) ecs.getComponent(otherEntity, CPositionString.class);
                     if (otherPosition == null || !otherPosition.roomTag.equals(positionString.roomTag)) {
                        toAdd.add(new COutDialog(commandInput.args.elementAt(1) + " is not in the room."));
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
                    CStorageCapacity storageCapacity = (CStorageCapacity) ecs.getComponent(otherEntity, CStorageCapacity.class);
                    CInventory otherInventory = (CInventory) ecs.getComponent(otherEntity, CInventory.class);
                    CPlayerWeight playerWeight = (CPlayerWeight) ecs.getComponent(e, CPlayerWeight.class);
                    CPlayerWeight otherWeight = (CPlayerWeight) ecs.getComponent(otherEntity, CPlayerWeight.class);
                    CName otherName = (CName) ecs.getComponent(otherEntity, CName.class);
                    if (itemWeight == null || storageCapacity == null || otherInventory == null || playerWeight == null || otherWeight == null || otherName == null) {
                        ecs.remove(e, commandInput);
                        return;
                    }

                    if (otherWeight.currentWeight + itemWeight.weight >= storageCapacity.storageCapacity) {
                        toAdd.add(new COutDialog(itemName + " is too heavy for " + otherName + "."));
                        ecs.remove(e, commandInput);
                        return;
                    }

                    playerInventory.inventory.remove(item);
                    otherInventory.inventory.add(item);
                    playerWeight.currentWeight -= itemWeight.weight;
                    otherWeight.currentWeight += itemWeight.weight;

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

