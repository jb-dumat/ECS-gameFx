import java.util.LinkedList;
import java.util.Queue;

public class SCommandDrop implements Command {
    /**
     * Processes the logic behind the drop command.
     * @param playerEntity the player to drop the item from
     * @param itemEntity the item entity to drop
     * @param roomEntity the room entity where to drop the item
     * @param playerInventory the playerInventory to remove the item from
     */
    private void dropLogic(Entity playerEntity, Entity itemEntity,
                           Entity roomEntity, CInventory playerInventory) {
        EcsManager ecs = EcsManager.getInstance();
        CWeight itemWeight = ecs.getComponent(itemEntity, CWeight.class);
        CInventory roomInventory = ecs.getComponent(roomEntity, CInventory.class);
        CWeight playerWeight = ecs.getComponent(playerEntity, CWeight.class);

        if (itemWeight == null || roomInventory == null || playerWeight == null) {
            return;
        }
        playerInventory.inventory.remove(itemEntity);
        roomInventory.inventory.add(itemEntity);
        playerWeight.weight -= itemWeight.weight;
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
     * @param playerInventory The player inventory
     * @param toAdd Concurrent modification handler
     * @return true if the players owns the item, otherwise false
     */
    private boolean checkPlayerInventory(Entity itemEntity, String itemName,
                                         CInventory playerInventory, Queue<IComponent> toAdd) {
        if (itemEntity == null || playerInventory == null || playerInventory.inventory == null ||
            !playerInventory.inventory.contains(itemEntity)) {
            toAdd.add(new COutDialog("You don't have the " + itemName + "."));
            return false;
        }
        return true;
    }

    private boolean checkItemArg(Entity e, CCommandInput commandInput, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();

        if (commandInput.args == null || commandInput.args.size() < 1) {
            toAdd.add(new COutDialog("Drop what ?"));
            ecs.remove(e, commandInput);
            return false;
        }
        return true;
    }

    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains(e -> {

                    // Fetch CCommandInput and check if it is valid
                    CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);
                    if (commandInput == null || commandInput.command == null || !commandInput.command.equals("drop"))
                        return;

                    // Check if command specifies an item
                    if (!this.checkItemArg(e, commandInput, toAdd))
                        return;

                    // Get the room where the character stands.
                    Entity roomEntity = this.getRoomEntity(e);
                    if (roomEntity == null)
                        return;

                    // Check if the player owns the item
                    String itemName = commandInput.args.elementAt(0);
                    Entity item = ecs.getEntityByComponentValue(CName.class, "name", itemName);
                    CInventory playerInventory = ecs.getComponent(e, CInventory.class);
                    if (this.checkPlayerInventory(item, itemName, playerInventory, toAdd)) {
                        // Tries to drop the item from the player's inventory
                        this.dropLogic(e, item, roomEntity, playerInventory);
                    }

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
