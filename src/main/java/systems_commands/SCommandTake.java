package systems_commands;

import core.*;
import ecs.*;
import components.*;
import java.util.LinkedList;
import java.util.Queue;

public class SCommandTake implements Command {
    /**
     * Processes the logic behind the take command.
     * @param playerEntity the player entity ID
     * @param itemEntity the item entity to take
     * @param roomInventory the roomInventory to remove the item from
     */
    private void takeLogic(Entity playerEntity, Entity itemEntity, CInventory roomInventory,
                           CCommandInput commandInput, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();
        CWeight itemWeight = ecs.getComponent(itemEntity, CWeight.class);
        CInventory playerInventory = ecs.getComponent(playerEntity, CInventory.class);
        CWeight playerWeight = ecs.getComponent(playerEntity, CWeight.class);
        CMaxWeight maxStorage = ecs.getComponent(playerEntity, CMaxWeight.class);

        if (itemWeight.weight + playerWeight.weight >= maxStorage.storageCapacity) {
            toAdd.add(new COutDialog(commandInput.args.elementAt(0) + " is too heavy."));
            ecs.remove(playerEntity, commandInput);
            return;
        }
        roomInventory.inventory.remove(itemEntity);
        playerInventory.inventory.add(itemEntity);
        playerWeight.weight += itemWeight.weight;
    }

    /**
     * Check the if player's inventory contains the item.
     * @param itemEntity The item entity
     * @param itemName The item name
     * @param roomInventory The player inventory
     * @param toAdd Concurrent modification handler
     * @return true if the room owns the item, otherwise false
     */
    private boolean checkRoomInventory(Entity itemEntity, String itemName,
                                       CInventory roomInventory, Queue<IComponent> toAdd) {
        if (!roomInventory.inventory.contains(itemEntity)) {
            toAdd.add(new COutDialog("No " + itemName + " in the room."));
            return false;
        }
        return true;
    }

    /**
     * Check if the item argument is valid
     * @param e entity player
     * @param commandInput input command
     * @param toAdd Concurrent modification handler
     * @return true if the command is valid, otherwise false
     */
    private boolean checkItemArg(Entity e, CCommandInput commandInput, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();

        if (commandInput.args.size() < 1) {
            toAdd.add(new COutDialog("Take what ?"));
            ecs.remove(e, commandInput);
            return false;
        }
        return true;
    }

    /**
     * The take command system handler
     */
    @Override
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains(e -> {
                    CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);

                    try {
                        // Fetch CCommandInput and check if it is valid
                        if (!commandInput.command.equals("take") || !this.checkItemArg(e, commandInput, toAdd))
                            return;

                        // Check the room's inventory and apply the system logic
                        Entity roomEntity = ecs.getEntityByComponentValue(CName.class, "name",
                                ecs.getComponent(e, CPositionString.class).roomTag);
                        Entity item = ecs.getEntityByComponentValue(CName.class, "name",
                                commandInput.args.elementAt(0));
                        CInventory roomInventory = ecs.getComponent(roomEntity, CInventory.class);

                        if (this.checkRoomInventory(item, commandInput.args.elementAt(0), roomInventory, toAdd)) {
                            this.takeLogic(e, item, roomInventory, commandInput, toAdd);
                        }

                        // Removes the command
                        ecs.remove(e, commandInput);

                    } catch (Exception ex) {
                        ecs.remove(e, commandInput);
                        GameCore.getInstance().getLogger().warning(ex.getMessage());
                    }
                },

                CCommandInput.class,
                CPositionString.class,
                CInventory.class,
                CWeight.class,
                CMaxWeight.class
        );

        // Add new stored entities to Ecs
        toAdd.forEach(c -> ecs.put(new Entity(), c));
    }
}
