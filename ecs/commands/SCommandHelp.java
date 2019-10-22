import java.util.LinkedList;
import java.util.Queue;

public class SCommandHelp implements Command {
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();
        Queue<IComponent> toAdd = new LinkedList<>();

        ecs.forEachIfContains((e) -> {
            CCommandInput commandInput = ((CCommandInput) ecs.getComponent(e, CCommandInput.class));

            if (commandInput.command.equals("help")) {
                toAdd.add(new COutDialog(helpMessage.toString()));
            }
        },
                CCommandInput.class);

        toAdd.forEach(c -> ecs.put(new Entity(), c));
    }

    public static StringBuilder helpMessage;
}
