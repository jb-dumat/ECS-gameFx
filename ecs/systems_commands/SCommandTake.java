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

        if (maxStorage == null || itemWeight == null || roomInventory == null ||
            playerWeight == null || (itemWeight.weight + playerWeight.weight >= maxStorage.storageCapacity)) {
            toAdd.add(new COutDialog(commandInput.args.elementAt(0).toString() + " is too heavy."));
            ecs.remove(playerEntity, commandInput);
            return;
        }
        roomInventory.inventory.remove(itemEntity);
        playerInventory.inventory.add(itemEntity);
        playerWeight.weight += itemWeight.weight;
    }

    /**
     * Get the room entity where the player stands
     * @param playerEntity the player entity
     * @return The room entity where the players stands, otherwise null
     */
    private Entity getRoomEntity(Entity playerEntity) {
        EcsManager ecs = EcsManager.getInstance();

        CPositionString positionString = ecs.getComponent(playerEntity, CPositionString.class);
        if (positionString == null)
            return null;
        return ecs.getEntityByComponentValue(CName.class, "name", positionString.roomTag);
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
        if (itemEntity == null || roomInventory == null || roomInventory.inventory == null ||
            !roomInventory.inventory.contains(itemEntity)) {
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

        if (commandInput.args == null || commandInput.args.size() < 1) {
            toAdd.add(new COutDialog("Take what ?"));
            ecs.remove(e, commandInput);
            return false;
        }
        return true;
    }

    /**
     * The take command system handler
     */
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains(e -> {

                    // Fetch CCommandInput and check if it is valid
                    CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);
                    if (commandInput == null || commandInput.command == null || !commandInput.command.equals("take"))
                        return;

                    // Check if command specifies an item
                    if (!this.checkItemArg(e, commandInput, toAdd))
                        return;

                    // Get the room where the character stands.
                    Entity roomEntity = this.getRoomEntity(e);
                    if (roomEntity == null) {
                        ecs.remove(e, commandInput);
                        return;
                    }

                    // Check if the player owns the item
                    String itemName = commandInput.args.elementAt(0);
                    Entity item = ecs.getEntityByComponentValue(CName.class, "name", itemName);
                    CInventory roomInventory = ecs.getComponent(roomEntity, CInventory.class);
                    if (!this.checkRoomInventory(item, itemName, roomInventory, toAdd)) {
                        ecs.remove(e, commandInput);
                        return;
                    }

                    // Take logic
                    this.takeLogic(e, item, roomInventory, commandInput, toAdd);

                    // Removes the command
                    ecs.remove(e, commandInput);
                },

                CCommandInput.class,
                CPositionString.class,
                CInventory.class,
                CMaxWeight.class
        );

        toAdd.forEach(c -> ecs.put(new Entity(), c));
    }
}
