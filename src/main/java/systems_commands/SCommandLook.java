package systems_commands;

import core.*;
import ecs.*;
import components.*;
import java.util.LinkedList;
import java.util.Queue;

public class SCommandLook implements Command {
    @Override
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains(e -> {

            // Check the validness of the command
            CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);
            CPositionString positionString = ecs.getComponent(e, CPositionString.class);
            if (commandInput == null || positionString == null)
                return;

            // Get the room where the player stands
            Entity roomEntity = ecs.getEntityByComponentValue(CName.class, "name", positionString.roomTag);
            if (roomEntity == null)
                return;

            // List all information about the room
            if (commandInput.command.equals("look")) {
                SCommandGo.listUserInfoConcurrent(roomEntity, toAdd);
                ecs.remove(e, commandInput);
            }

        },
                CCommandInput.class,
                CPositionString.class
        );

        toAdd.forEach(c -> ecs.put(new Entity(), c));
    }
}
