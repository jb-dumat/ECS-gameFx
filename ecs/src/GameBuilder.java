import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameBuilder {
    private GameBuilder() {
        this.rooms = new LinkedHashMap<>();
        this.players = new LinkedHashMap<>();
        this.items = new LinkedHashMap<>();
        this.ecs = EcsManager.getInstance();
    }

    public void generateGame() {
        // Create the welcome scene
        this.createWelcomeScene();

        // Create the rooms
        this.createRooms();

        // Create the rooms inventory
        this.createRoomInventory();

        // Create the players
        this.createPlayers();

        // Create the commands schemes
        this.createCmdSchemes();
    }

    private void createWelcomeScene() {
        ecs.put(new Entity(), new COutDialog("Welcome to the world of Zuul!\nWorld of Zuul is a new, incredibly boring adventure game.\nType 'help' if you need help.\n"));
    }

    private void createRooms() {
        Entity outside = new Entity(), theatre = new Entity(), pub = new Entity(), lab = new Entity(), office = new Entity();

        // Create outside
        ecs.put(outside,
                new CName("outside"),
                new CDescription("outside the main entrance of the university"),
                new CExit("theatre", "east", theatre),
                new CExit("lab", "south", lab),
                new CExit("pub", "west", pub)
        );

        ecs.put(theatre,
                new CName("theatre"),
                new CDescription("in a lecture theatre"),
                new CExit( "outside", "west", outside),
                new CInventory()
        );

        ecs.put(pub,
                new CName("pub"),
                new CDescription("in the campus pub"),
                new CExit( "outside", "east", outside),
                new CInventory()
        );

        ecs.put(lab,
                new CName("lab"),
                new CDescription("in a computing lab"),
                new CExit("outside", "north", outside),
                new CExit("office", "east", office),
                new CInventory()
        );

        ecs.put(office,
                new CName("office"),
                new CDescription("in the computing admin office"),
                new CExit("lab", "west", lab),
                new CInventory()
        );

        rooms.put("outside", outside);
        rooms.put("theatre", theatre);
        rooms.put("pub", pub);
        rooms.put("lab", lab);
        rooms.put("office", office);
    }

    private void createPlayers() {
        Entity player1 = new Entity();

        ecs.put(player1,
                new CName("player1"),
                new CPositionString("outside"),
                new CInventory(),
                new CPlayerWeight(0),
                new CStorageCapacity(10),
                new CInDialog()
        );

        players.put("player1", player1);

        SCommandGo.listUserInfo(this.rooms.get("outside"));
    }

    private void createRoomInventory() {
        Entity notebook = new Entity();

        ecs.put(notebook,
                new CName("Notebook"),
                new CWeight(2)
        );

        ecs.put(rooms.get("outside"),
                new CInventory(notebook)
        );

        this.items.put("notebook", notebook);
    }

    private void createCmdSchemes() {
        Entity broker = new Entity();
        Map<String, Constructor<? extends Command>> schemes = new LinkedHashMap<>();

        try {
            ecs.put(broker,
                    new CCommandSchemes(schemes),
                    new CCommandList());

            schemes.put("help", SCommandHelp.class.getConstructor());
            schemes.put("quit", SCommandQuit.class.getConstructor());
            schemes.put("go", SCommandGo.class.getConstructor());
            schemes.put("look", SCommandLook.class.getConstructor());
            schemes.put("take", SCommandTake.class.getConstructor());
            schemes.put("drop", SCommandDrop.class.getConstructor());
            schemes.put("give", SCommandGive.class.getConstructor());

            SCommandHelp.helpMessage = new StringBuilder("You are lost. You are alone. You wander\naround at the university.\n\nYour command words are: ");
            schemes.forEach((s, c) -> { SCommandHelp.helpMessage.append(s).append(" "); });
            SCommandHelp.helpMessage.deleteCharAt(SCommandHelp.helpMessage.length() - 1);

        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        }
    }

    private EcsManager ecs;
    private Map<String, Entity> rooms;
    private Map<String, Entity> players;
    private Map<String, Entity> items;

    public static GameBuilder getInstance() {
        if (Instance == null) {
            Instance = new GameBuilder();
        }
        return Instance;
    }

    private static GameBuilder Instance;
}
