import java.lang.reflect.Constructor;
import java.util.*;

/**
 * The Zuul Bad game builder. It provides a way
 * to build statically a game.
 * If you need to change the
 * way the game is built, you must make another GameBuilder
 * class which inherits from IGameBuilder.
 */
public class GameBuilder implements IGameBuilder {

    /**
     * Constructs a GameBuilder which uses a unique reference of an EcsManager
     * @param ecs EcsManager reference
     */
    public GameBuilder(EcsManager ecs) {
        this.rooms = new LinkedHashMap<>();
        this.ecs = ecs;
    }

    /**
     * Builds statically the game.
     */
    public void buildGame() {
        // Create the welcome scene
        this.createWelcomeScene();

        // Create the rooms
        this.createRooms();

        // Create the rooms inventory
        this.createRoomInventory();

        // Create the players
        this.createPlayer();

        // Create the commands schemes
        this.createCmdSchemes();
    }

    /**
     * Creates the "Welcome scene"
     */
    private void createWelcomeScene() {
        ecs.put(new Entity(), new COutDialog("Welcome to the world of Zuul!\nWorld of Zuul is a new, incredibly boring adventure game.\nType 'help' if you need help.\n"));
    }

    /**
     * Creates all the rooms
     */
    private void createRooms() {
        Entity outside = new Entity(), theatre = new Entity(), pub = new Entity(), lab = new Entity(), office = new Entity();

        // Create outside room
        ecs.put(outside,
                new CName("outside"),
                new CDescription("outside the main entrance of the university"),
                new CExit("theatre", "east", theatre),
                new CExit("lab", "south", lab),
                new CExit("pub", "west", pub)
        );

        // Create theatre room
        ecs.put(theatre,
                new CName("theatre"),
                new CDescription("in a lecture theatre"),
                new CExit( "outside", "west", outside),
                new CInventory()
        );

        // Create pub room
        ecs.put(pub,
                new CName("pub"),
                new CDescription("in the campus pub"),
                new CExit( "outside", "east", outside),
                new CInventory()
        );

        // Create lab room
        ecs.put(lab,
                new CName("lab"),
                new CDescription("in a computing lab"),
                new CExit("outside", "north", outside),
                new CExit("office", "east", office),
                new CInventory()
        );

        // Create office room
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

    /**
     * Create a new human player.
     */
    private void createPlayer() {
        Entity player1 = new Entity(), player2 = new Entity();

        // Makes the player entity
        ecs.put(player1,
                new CName("player1"),
                new CPositionString("outside"),
                new CInventory(),
                new CWeight("0"),
                new CMaxWeight("10"),
                new CInPlayer()
        );

        // Makes the computer entity
        ecs.put(player2,
                new CName("player2"),
                new CPositionString("outside"),
                new CInventory(),
                new CWeight("0"),
                new CMaxWeight("10"),
                new CComputerLevel(CComputerLevel.Level.BRAINLESS)
        );

        SCommandGo.listUserInfo(this.rooms.get("outside"));
    }

    /**
     * Initializes the outside room's inventory
     */
    private void createRoomInventory() {
        Entity notebook = new Entity();

        // Create a new entity Notebook
        ecs.put(notebook,
                new CName("Notebook"),
                new CWeight("2")
        );

        // Add the Notebook to the outside inventory
        ecs.put(rooms.get("outside"),
                new CInventory(notebook)
        );
    }

    /**
     * This methods makes the commands scheme.
     * The whole command pattern is handled as an entity by
     * the ecs manager. This means that each command logic is
     * processed as a system.
     */
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
}
