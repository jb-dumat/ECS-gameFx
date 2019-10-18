import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        EcsManager ecs = EcsManager.getInstance();
        SystemManager systemManager = new SystemManager();
        CommandPool cmdPool = CommandPool.getInstance();
        ExecutorService pool = Executors.newFixedThreadPool(1);

        // Launch Service Loop in ThreadPool
        pool.execute(systemManager);

        /*

        //Set outside inventory
        Entity notebook = ecs.attachComponent(outside, new CStorable(outside, 2));
        ecs.attachComponent(notebook, new CName("Notebook"));

        // Launch game
        Entity roomHint = ((CPositionHint) ecs.getComponentsPool().get("CPositionHint").get(player).toArray()[0]).positionEntity;
        ecs.attachComponent(new Entity(), new COutDialog("You are " + ((CDescription) ecs.getComponentsPool().get("CDescription").get(roomHint).toArray()[0]).description));

        // Get exits direction tags
        String msg = new String("Exits: ");
        for (int it = 0; it < ecs.getComponentsPool().get("CExit").get(outside).size(); it++) {
            msg += ((CExit) ecs.getComponentsPool().get("CExit").get(outside).toArray()[it]).directionTag + " ";
        }
        ecs.attachComponent(new Entity(), new COutDialog(msg));

        // Start the input dialog
        ecs.attachComponent(new Entity(), new CInDialog(player));


         */
    }
}
