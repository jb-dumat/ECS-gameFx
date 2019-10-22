import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class SCommandGo implements Command {
    public SCommandGo() {}

    private static StringBuilder listExits(Entity roomEntity) {
        EcsManager ecs = EcsManager.getInstance();
        StringBuilder msg = new StringBuilder("\nExits: ");
        Collection<IComponent> exits = ecs.getComponents(roomEntity, CExit.class);

        if (exits.size() != 0)
            for (IComponent exit : exits) {
                msg.append(((CExit) exit).directionTag).append(" ");
            }
        msg.deleteCharAt(msg.length() - 1);
        return msg;
    }

    private static StringBuilder listItems(Entity roomEntity) {
        EcsManager ecs = EcsManager.getInstance();
        StringBuilder msg = new StringBuilder("\nItems:");

        CInventory inventory = (CInventory) ecs.getComponent(roomEntity, CInventory.class);

        if (inventory != null)
            for (Entity item : inventory.inventory) {
                CName name = (CName) ecs.getComponent(item, CName.class);
                CWeight weight = (CWeight) ecs.getComponent(item, CWeight.class);

                if (name != null && weight != null)
                    msg.append(" ").append(name.name).append("(").append(weight.weight).append(")");
            }

        return msg;
    }

    public static void listUserInfo(Entity roomEntity) {
        EcsManager ecs = EcsManager.getInstance();
        String msg = "You are " + ((CDescription) ecs.getComponent(roomEntity, CDescription.class)).description +
                listExits(roomEntity) + listItems(roomEntity);

        ecs.put(new Entity(), new COutDialog(msg));
    }

    public static void listUserInfoConcurrent(Entity roomEntity, Queue<IComponent> toAdd) {
        EcsManager ecs = EcsManager.getInstance();
        String msg = "You are " + ((CDescription) ecs.getComponent(roomEntity, CDescription.class)).description +
                     listExits(roomEntity) + listItems(roomEntity);

        toAdd.add(new COutDialog(msg));
    }

    private static Entity checkPosition(Entity e, CCommandInput commandInput) {
        EcsManager ecs = EcsManager.getInstance();
        CPositionString positionString = ((CPositionString) ecs.getComponent(e, CPositionString.class));
        Entity room = ecs.getEntityByValue(CName.class, "name", positionString.roomTag);

        if (room == null)
            return null;

        CExit exit = ((CExit) ecs.getEntityComponentByValue(room, CExit.class, "directionTag", commandInput.args.get(0)));

        if (exit == null)
            return null;

        positionString.roomTag = exit.exitTag;

        return exit.exitID;
    }

    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains((e) -> {
            CCommandInput commandInput = (CCommandInput) ecs.getComponent(e, CCommandInput.class);

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
