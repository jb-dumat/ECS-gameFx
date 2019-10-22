import java.util.LinkedList;
import java.util.Queue;

public class SCommandLook implements Command {
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
