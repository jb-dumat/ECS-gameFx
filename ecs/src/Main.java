import java.util.*;
import java.util.Map;

public class Main {
    private static long clock = 0 ;

    public static void restart() {
        clock = java.lang.System.currentTimeMillis() ;
    }

    public static long getDuration() {
        return java.lang.System.currentTimeMillis() - clock;
    }

    public static void main(String[] args) {
        EcsManager ecs = EcsManager.getInstance();
        CommandPool cmdPool = CommandPool.getInstance();
        restart();

        // Create entity command
        Vector<String> cVector1 = new Vector<>(), cVector2 = new Vector<>();
        cVector1.add("north");

        Entity e = ecs.attachComponent(new Entity(), new CCommandInput("go", cVector1));

        cVector2.add("south");

        ecs.attachComponent(e, new CCommandInput("go", cVector2));

        ecs.attachComponent(e, new CPosition1D("room1"));

        ecs.attachComponent(new Entity(), new CExit("room1", "room2", "north"));

        // Store new command model
        cmdPool.addCommand("go", 1);

        while (true) {
            if (getDuration() > 1000) {
                ecs.update();
                restart();
            }
        }
    }
}
