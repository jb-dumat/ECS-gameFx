/**
 * The quit command handler
 * Display a message then exits
 * You can manage the exit process in another way. Just make another
 * Quit command System
 */
public class SCommandQuit implements Command {
    public void execute() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains(e -> {
            CCommandInput commandInput = ecs.getComponent(e, CCommandInput.class);

            if (commandInput.command.equals("quit")) {
                System.out.println("Thank you for playing.\tGood bye.");
                System.exit(0);
            }
        }, CCommandInput.class);
    }

}