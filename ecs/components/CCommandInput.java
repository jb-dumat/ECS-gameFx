import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class CCommandInput implements IComponent {
    CCommandInput(String command, Vector<String> args) {
        this.command = command;
        this.args = args;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public String command;
    public Vector<String> args;
 }
