import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CCommandList implements IComponent {
    CCommandList() {
        this.commandList = new ArrayList<>();
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public List<Command> commandList;
}
