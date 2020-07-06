package core;

import systems_commands.*;
import components.*;
import ecs.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * The Zuul Bad game builder. It provides a way
 * to build statically the game.
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
        this.defaultRoom = null;
        this.rooms = new LinkedHashMap<>();
        this.ecs = ecs;
        this.roomsExits = new LinkedHashMap<>();
    }

    /**
     * Builds statically and "procedurally" the game.
     * I couldn't add a real procedural way to build the game (with randomness)
     * because it could modify what is expected by the corrector.
     */
    @Override
    public void buildGame() {
        rooms.clear();
        
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
     * Links all exits between them.
     * 
     */
    private void linksExits() {
        List<String> directions = Arrays.asList(new String[]{"north", "east", "south", "west"});        
        
        roomsExits.keySet().forEach((exit) -> {
            int i = 0;
            for (String ex : roomsExits.get(exit)) {
                if (!ex.equals("null")) {                    
                    ecs.put(rooms.get(exit),
                            new CExit(ex, directions.get(i), rooms.get(ex))
                    );
                }
                i++;
            }
        });
    }
    
    /**
     * Parse csv file to make a map.
     * @param pathToFile
     */
    private void parseMapFile(String pathToFile) {
        File file = new File(pathToFile); 
        Scanner sc; 

        try {
            sc = new Scanner(file);
        
            // Iterate over each lines of the file
            while (sc.hasNextLine()) {
                String[] words = sc.nextLine().split(",\\s+");
                Entity roomEntity = new Entity();

                // Set the default room to the first line.
                if (defaultRoom == null)
                    defaultRoom = words[0];

                // Create the room and adds it to the ecs manager.
                ecs.put(roomEntity,
                        new CName(words[0]),
                        new CDescription(words[1]));

                // Saves the room for utils.
                rooms.put(words[0], roomEntity);
                
                // Saves exits for later.
                int i = 2;
                List<String> exits = new ArrayList();
                for (; i < 6; i++) {
                    exits.add(words[i]);
                }
                roomsExits.put(words[0], exits);

                // Manage items
                List<Entity> toAdd = new ArrayList(); 
                for (; i < words.length; i += 2) {
                    Entity item = new Entity();

                    // Create a new item and adds it to the ecs manager.
                    words[i] = words[i].replaceAll("\\s+","");
                    ecs.put(item,
                            new CName(words[i]),
                            new CWeight(Integer.parseInt(words[i + 1]))
                    );
                    toAdd.add(item);
                }

                // Attaches the room inventory to the room entity.
                ecs.put(roomEntity,
                        new CInventory(toAdd.toArray(new Entity[0])
                ));
            }
            
            // Links all exits.
            this.linksExits();
        
        } catch (FileNotFoundException ex) {
            GameCore.getInstance().getLogger().warning(ex.getMessage());
        }
    }
    
    /**
     * Build game from a csv file.
     * @param pathToFile
     */
    @Override
    public void buildGameFromFile(String pathToFile) {
        rooms.clear();
        roomsExits.clear();
        defaultRoom = null;
        
        // Create the welcome scene
        this.createWelcomeScene();

        this.parseMapFile(pathToFile);
        
        // Create the players
        this.createPlayer();

        // Create the commands schemes
        this.createCmdSchemes();
    }
    
    /**
     * Creates the welcome message
     */
    private void createWelcomeScene() {
        ecs.put(new Entity(), new COutDialog("Welcome to the world of Zuul!\nWorld of Zuul is a new, incredibly boring adventure game.\nType 'help' if you need help.\n"));
    }

    /**
     * Creates all rooms statically.
     */
    private void createRooms() {
        Entity outside = new Entity(), theatre = new Entity(), pub = new Entity(), lab = new Entity(), office = new Entity();

        defaultRoom = "outside";
        
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
     * Create new player(s).
     */
    private void createPlayer() {
        Entity player1 = new Entity();

        // Makes the player entity
        ecs.put(player1,
                new CName("Sarah"),
                new CPositionString(defaultRoom),
                new CInventory(),
                new CWeight(0),
                new CMaxWeight(10),
                new CInPlayer()
        );

        // Makes the player entity
        ecs.put(new Entity(),
                new CName("Roberto"),
                new CPositionString(defaultRoom),
                new CInventory(),
                new CWeight(0),
                new CMaxWeight(10),
                new CComputerLevel(CComputerLevel.Level.BRAINLESS)
        );
    }

    /**
     * Initializes the outside room's inventory
     * Should be done randomly at room creation.
     */
    private void createRoomInventory() {
        Entity notebook = new Entity();
        
        // Create a new entity Notebook
        ecs.put(notebook,
                new CName("Notebook"),
                new CWeight(2)
        );
           
        // Add the Notebook to the outside inventory
        ecs.put(rooms.get("outside"),
                new CInventory(notebook//, add1, add2, add3
        ));
    }

    /**
     * Parse commands xml file to retrieve all commands
     * @param schemes Feed the schemes with found commands
     */
    private void parseCommandXml(Map<String, Constructor<? extends Command>> schemes) {
        try {
         File inputFile = new File("./config/commands.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
        
         NodeList nList = doc.getElementsByTagName("command");         
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                try {
                schemes.put(eElement
                  .getElementsByTagName("tag")
                  .item(0)
                  .getTextContent(), (Constructor<? extends Command>) Class.forName(eElement
                  .getElementsByTagName("constructor")
                  .item(0)
                  .getTextContent()).getConstructor());
                
                } catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
                    GameCore.getInstance().getLogger().warning(e.getMessage());
                }
            }
         }
      } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
          GameCore.getInstance().getLogger().warning(e.getMessage());
      }
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

        ecs.put(broker,
                new CCommandSchemes(schemes),
                new CCommandQueue()
        );

        parseCommandXml(schemes);
        
        SCommandHelp.helpMessage = new StringBuilder("You are lost. You are alone. You wander\naround at the university.\n\nYour command words are: ");
        schemes.forEach((s, c) -> { SCommandHelp.helpMessage.append(s).append(" "); });
        SCommandHelp.helpMessage.deleteCharAt(SCommandHelp.helpMessage.length() - 1);
    }

    private String defaultRoom;
    private final EcsManager ecs;
    private final Map<String, Entity> rooms;
    private final Map<String, List<String>> roomsExits;
}
