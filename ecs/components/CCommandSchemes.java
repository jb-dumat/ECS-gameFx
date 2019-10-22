import javax.script.Invocable;
import java.lang.reflect.Constructor;
import java.util.Map;

public class CCommandSchemes implements IComponent {
    CCommandSchemes(Map<String, Constructor<? extends Command>> schemes) { this.schemes = schemes; }

    public final String getComponentName() { return this.getClass().getName(); }

    public Map<String, Constructor<? extends  Command>> schemes;
}
