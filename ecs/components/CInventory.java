import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CInventory implements IComponent {
    CInventory(Entity... entities) {
        this.inventory = new ArrayList<Entity>();
        this.inventory.addAll(Arrays.asList(entities));
    }

    public final String getComponentName() { return this.getClass().getName(); }

    List<Entity> inventory;
}
