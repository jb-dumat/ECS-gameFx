import java.util.HashMap;
import java.util.Map;

public class CommandPool {
    private CommandPool() {
        this.commandModel = new HashMap<>();
    }

    public void addCommand(String commandTag, int commandParamNumber) {
        this.commandModel.put(commandTag, commandParamNumber);
    }

    public boolean contains(String commandTag) { return this.commandModel.containsKey(commandTag); }

    public int getValue(String commandTag) {
        return commandModel.get(commandTag);
    }

    public void printKeys() {
        for (String key : commandModel.keySet()) {
            System.out.println(key);
        }
    }

    public void removeCommand(String commandTag) {
        this.commandModel.remove(commandTag);
    }

    public static CommandPool getInstance() {
        if (Instance == null) {
            Instance = new CommandPool();
            return Instance;
        }
        return Instance;
    }

    private static CommandPool Instance;
    private Map<String, Integer> commandModel;
}
