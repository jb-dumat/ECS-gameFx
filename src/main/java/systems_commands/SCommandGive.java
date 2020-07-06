package systems_commands;

import core.*;
import ecs.*;
import components.*;
import java.util.LinkedList;
import java.util.Queue;

public class SCommandGive implements Command {
    /**
     * Process the give logic when all components have been checked.
     * @param item the item entity ID
     * @param playerWeight the player's weight
     * @param otherWeight the other's player weight
     * @param itemWeight the item's wiehgt
     * @param playerInventory the player's inventory
     * @param otherInventory the other player's inventory
     */
    private void giveLogic(Entity item, CWeight playerWeight, CWeight otherWeight,
                           CWeight itemWeight, CInventory playerInventory, CInventory otherInventory) {
        playerInventory.inventory.remove(item);
        otherInventory.inventory.add(item);
        playerWeight.weight -= itemWeight.weight;
        otherWeight.weight += itemWeight.weight;
    }

    /**
     * Check if the command is a valid "give" command
     * @param playerEntity the player entity ID
     * @param commandInput the player's command input
     * @param toAdd Concurrent modificatin handler
     * @return true if command is valid, otherwise false
     */
    private boolean checkCommandArgs(Entity playerEntity, CCommandInput commandInput, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();
        boolean result = true;

        if (commandInput.args == null || commandInput.args.size() < 1) {
            toAdd.add(new COutDialog("Give what ?"));
            ecs.remove(playerEntity, commandInput);
            result = false;
        } else if (commandInput.args.size() < 2) {
            toAdd.add(new COutDialog("Give to who ?"));
            ecs.remove(playerEntity, commandInput);
            result = false;
        }
        return result;
    }

    /**
     * Check is the other character has sufficient weight capacity
     * to hold the given item.
     * @param otherEntity the other entity
     * @param otherInventory the other inventory
     * @param itemWeight the item weight
     * @param otherWeight the other weight
     * @param commandInput the command input
     * @param toAdd Concurrent modification handler
     * @return true if the player can hold the item, otherwise false
     */
    private boolean checkOtherWeightCapacity(Entity otherEntity, CInventory otherInventory,
                                             CWeight itemWeight, CWeight otherWeight,
                                             CCommandInput commandInput, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();
        CMaxWeight storageCapacity = ecs.getComponent(otherEntity, CMaxWeight.class);
        String itemName = commandInput.args.elementAt(0);
        CName otherName = ecs.getComponent(otherEntity, CName.class);

        if (otherWeight.weight + itemWeight.weight >= storageCapacity.storageCapacity) {
            toAdd.add(new COutDialog(itemName + " is too heavy for " + otherName.name + "."));
            return false;
        }

        return true;
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

    /**
     * Check if both players stands at the same position
     * @param playerEntity The giver entity
     * @param otherEntity the receiver entity
     * @param commandInput the command
     * @param toAdd Concurrent modification handler
     * @return true if players stands at the same position, otherwise false
     */
    private boolean checkPlayersPosition(Entity playerEntity, Entity otherEntity,
                                         CCommandInput commandInput, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();
        CPositionString positionString = ecs.getComponent(playerEntity, CPositionString.class);
        CPositionString otherPosition = ecs.getComponent(otherEntity, CPositionString.class);

        if (positionString == null || otherPosition == null || !otherPosition.roomTag.equals(positionString.roomTag)) {
            toAdd.add(new COutDialog(commandInput.args.elementAt(1) + " is not in the room."));
            ecs.remove(playerEntity, commandInput);
            return false;
        }
        return true;
    }

    /**
     * Check the validness of the receiver entity
     * @param otherEntity the receiver entity
     * @param playerEntity the current owner entity
     * @param commandInput the command
     * @param toAdd the concurrent modification handler
     * @return true if other exists, otherwise false
     */
    private boolean checkOtherEntity(Entity otherEntity, Entity playerEntity,
                                     CCommandInput commandInput, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();

        if (otherEntity == null) {
            toAdd.add(new COutDialog(commandInput.args.elementAt(1) + " doesn't exist."));
            ecs.remove(playerEntity, commandInput);
            return false;
        } else if (otherEntity == playerEntity) {
            toAdd.add(new COutDialog("You can't give something to yourself."));
            ecs.remove(playerEntity, commandInput);
            return false;
        }
        return true;
    }

    /**
     * The Give command system handler.
     */
    @Override
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains(e -> {
                    // Check if command and its args are a "give" valid request.
                    CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);

                    try {
                        if (commandInput == null || commandInput.command == null ||
                                !commandInput.command.equals("give") || !this.checkCommandArgs(e, commandInput, toAdd))
                            return;

                        // Check the validness of the other entity (Existence + matching positions).
                        Entity otherEntity = ecs.getEntityByComponentValue(CName.class, "name", commandInput.args.elementAt(1));
                        if (!this.checkOtherEntity(otherEntity, e, commandInput, toAdd) || !this.checkPlayersPosition(e, otherEntity, commandInput, toAdd))
                            return;

                        // Check the player's inventory
                        String itemName = commandInput.args.elementAt(0);
                        Entity item = ecs.getEntityByComponentValue(CName.class, "name", itemName);
                        CInventory playerInventory = ecs.getComponent(e, CInventory.class);
                        if (!this.checkPlayerInventory(item, itemName, playerInventory, toAdd))
                            return;

                        // Check if the other player is able to carry the item
                        CWeight itemWeight = ecs.getComponent(item, CWeight.class);
                        CInventory otherInventory = ecs.getComponent(otherEntity, CInventory.class);
                        CWeight otherWeight = ecs.getComponent(otherEntity, CWeight.class);
                        CWeight playerWeight = ecs.getComponent(e, CWeight.class);
                        if (this.checkOtherWeightCapacity(otherEntity, otherInventory, itemWeight, otherWeight, commandInput, toAdd)) {
                            // Process the "give" command logic
                            this.giveLogic(item, playerWeight, otherWeight, itemWeight, playerInventory, otherInventory);
                        }

                        // Remove the command
                        ecs.remove(e, commandInput);
                    } catch (Exception ex) {
                        GameCore.getInstance().getLogger().warning(ex.getMessage());
                        ecs.remove(e, commandInput);
                    }
                },
                CCommandInput.class,
                CPositionString.class,
                CInventory.class,
                CMaxWeight.class
        );

        toAdd.forEach(c -> ecs.put(new Entity(), c));
    }
}

