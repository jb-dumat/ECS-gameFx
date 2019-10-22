import java.util.Vector;

public class CCommandInput implements IComponent {
    CCommandInput(String command, Vector<String> args) {
        this.command = command;
        this.args = args;
    }

    public String command;
    public Vector<String> args;
 }
