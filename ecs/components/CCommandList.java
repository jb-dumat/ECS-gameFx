import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The component CCommandList contains a list of Command which
 * are used by the Broker System (Command Pattern)
 */
public class CCommandList implements IComponent {
    CCommandList() {
        this.commandList = new ArrayList<>();
    }

    public List<Command> commandList;
}
