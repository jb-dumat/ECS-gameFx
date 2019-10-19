public class SMovement {
    private static boolean checkPosition(Entity e, CCommandInput commandInput) {
        EcsManager ecs = EcsManager.getInstance();

        CPositionString positionString = ((CPositionString) ecs.getComponent(e, CPositionString.class));

        Entity room = ecs.getEntityByValue(CName.class, "name", positionString.roomTag);

        if (room == null)
            return false;

        System.out.println(commandInput.args);

        CExit exit = ((CExit) ecs.getEntityComponentByValue(room, CExit.class, "directionTag", commandInput.args.get(0)));

        if (exit == null)
            return false;

        positionString.roomTag = exit.exitTag;

        return true;
    }

    public static void update() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachWith((e) -> {
            CCommandInput commandInput = (CCommandInput) ecs.getComponent(e, CCommandInput.class);

            if (commandInput.command != null && commandInput.command.equals("go") &&
                commandInput.args != null && commandInput.args.size() > 0) {
                if (checkPosition(e, commandInput)) {

                }
            }
        },
                CPositionString.class,
                CCommandInput.class
        );
    }

}
