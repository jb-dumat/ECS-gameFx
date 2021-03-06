package components;

import core.Command;
import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * The component CCommandSchemes contains some or all
 * of the commands handled.
 */
public class CCommandSchemes implements IComponent {
    public CCommandSchemes(Map<String, Constructor<? extends Command>> schemes) { this.schemes = schemes; }

    public Map<String, Constructor<? extends  Command>> schemes;
}
