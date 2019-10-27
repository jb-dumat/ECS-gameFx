import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An Inventory component.
 */
public class CInventory implements IComponent {
    CInventory(Entity... entities) {
        this.inventory = new ArrayList<Entity>();
        if (entities.length > 0)
            this.inventory.addAll(Arrays.asList(entities));
    }

    public List<Entity> inventory;
}
