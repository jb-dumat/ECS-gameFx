public class SCommandQuit implements Command {

    public void execute() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains(e -> {
            CCommandInput commandInput = ((CCommandInput) ecs.getComponent(e, CCommandInput.class));

            if (commandInput.command.equals("quit")) {
                System.out.println("Thank you for playing.\tGood bye.");
                Main.pool.shutdown();
                System.exit(0);
            }
        }, CCommandInput.class);
    }

}