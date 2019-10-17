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

        // Store new command model
        cmdPool.addCommand("go", 1);

        // Welcome scene
        ecs.attachComponent(new Entity(), new COutDialog("Welcome to the world of Zuul!\nWorld of Zuul is a new, incredibly boring adventure game.\nType 'help' if you need help.\n"));

        // Create rooms
        Entity outside = ecs.attachComponent(new Entity(), new CDescription("outside the main entrance of the university"));
        Entity theatre = ecs.attachComponent(new Entity(), new CDescription("in a lecture theatre"));
        Entity pub = ecs.attachComponent(new Entity(), new CDescription("in the campus pub"));
        Entity lab = ecs.attachComponent(new Entity(), new CDescription("in a computing lab"));
        Entity office = ecs.attachComponent(new Entity(), new CDescription("in the computing admin office"));

        // Set outside exits
        ecs.attachComponent(outside, new CExit("outside", "theatre", "east", theatre));
        ecs.attachComponent(outside, new CExit("outside", "lab", "south", lab));
        ecs.attachComponent(outside, new CExit("outside", "pub", "west", pub));

        // Set theatre exits
        ecs.attachComponent(theatre, new CExit("theatre", "outside", "west", outside));

        // Set pub exits
        ecs.attachComponent(pub, new CExit("pub", "outside", "east", outside));

        // Set lab exits
        ecs.attachComponent(lab, new CExit("lab", "outside", "north", outside));
        ecs.attachComponent(lab, new CExit("lab", "office", "east", office));

        // Set office exits
        ecs.attachComponent(office, new CExit("office", "lab", "west", lab));

        // Create Player
        Entity player = new Entity();
        ecs.attachComponent(player, new CPosition1D("outside"));
        ecs.attachComponent(player, new CPositionHint(outside));

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
    }
}
