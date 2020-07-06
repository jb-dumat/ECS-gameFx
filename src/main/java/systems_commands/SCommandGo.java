package systems_commands;

import core.*;
import ecs.*;
import components.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class SCommandGo implements Command {
    public SCommandGo() {}

    /**
     * Constructs and append all exits if the current room into a string builder.
     * @param roomEntity The current room entity
     * @return A string buffer
     */
    private static StringBuilder listExits(Entity roomEntity) {
        EcsManager ecs = EcsManager.getInstance();
        StringBuilder msg = new StringBuilder("\nExits: ");
        Collection<CExit> exits = ecs.getComponents(roomEntity, CExit.class);

        if (!exits.isEmpty())
            exits.forEach((exit) -> {
                msg.append(exit.directionTag).append(" ");
        });
        msg.deleteCharAt(msg.length() - 1);
        return msg;
    }

    /**
     * Constructs and append all items if the current room into a string builder.
     * @param roomEntity The current room entity
     * @return A string buffer
     */
    private static StringBuilder listItems(Entity roomEntity) {
        EcsManager ecs = EcsManager.getInstance();
        StringBuilder msg = new StringBuilder("\nItems:");
        CInventory inventory = ecs.getComponent(roomEntity, CInventory.class);

        if (inventory != null)
            inventory.inventory.forEach((item) -> {
                CName name = ecs.getComponent(item, CName.class);
                CWeight weight = ecs.getComponent(item, CWeight.class);
            if (name != null && weight != null) {
                msg.append(" ").append(name.name).append("(").append(weight.weight).append(")");
            }
        });
        return msg;
    }

    /**
     * Build a buffer holding all informations about exits and items in the current room.
     * @param roomEntity
     * @return A string buffer
     */
    private static String buildUserInfo(Entity roomEntity) {
        return "You are " + EcsManager.getInstance().getComponent(roomEntity, CDescription.class).description + listExits(roomEntity) + listItems(roomEntity);
    }

    /**
     * Manage the user's room info.
     * @param roomEntity
     */
    public static void listUserInfo(Entity roomEntity) {
        EcsManager.getInstance().put(new Entity(), new COutDialog(buildUserInfo(roomEntity)));
    }

    /**
     * Manage the user's room info taking concurrent modification in account.
     * @param roomEntity
     * @param toAdd
     */
    public static void listUserInfoConcurrent(Entity roomEntity, Queue<IComponent> toAdd) {
        toAdd.add(new COutDialog(buildUserInfo(roomEntity)));
    }

    /**
     * Check the player's position
     * @param e player entity ID
     * @param commandInput The player's command
     * @return the new player's position
     */
    private static Entity checkPosition(Entity e, CCommandInput commandInput) {
        EcsManager ecs = EcsManager.getInstance();
        CPositionString positionString = ecs.getComponent(e, CPositionString.class);

        if (positionString == null)
            return null;

        Entity roomEntity = ecs.getEntityByComponentValue(CName.class, "name", positionString.roomTag);
        CExit exit = ecs.getComponentByValue(roomEntity, CExit.class, "directionTag", commandInput.args.get(0));

        if (exit == null)
            return null;

        positionString.roomTag = exit.exitTag;

        return exit.exitID;
    }

    /**
     * The go command system handler
     */
    @Override
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains((e) -> {
            CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);

            if (commandInput.command != null && commandInput.command.equals("go")) {
                if (commandInput.args != null && commandInput.args.size() > 0) {
                    Entity roomEntity = checkPosition(e, commandInput);
                    if (roomEntity != null) {
                        listUserInfoConcurrent(roomEntity, toAdd);
                    } else {
                        toAdd.add(new COutDialog("There is no door!"));
                    }
                } else {
                    toAdd.add(new COutDialog("Go where ?"));
                }
            }

            ecs.remove(e, commandInput);
            },
                CPositionString.class,
                CCommandInput.class
        );
        toAdd.forEach(c -> { ecs.put(new Entity(), c); });
    }

}
