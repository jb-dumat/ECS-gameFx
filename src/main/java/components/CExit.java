package components;

import ecs.Entity;

/**
 * An Exit component.
 */
public class CExit implements IComponent {
    public CExit(String exitTag, String directionTag, Entity exitID)
    {
        this.exitTag = exitTag;
        this.directionTag = directionTag;
        this.exitID = exitID;
    }

    public String exitTag;
    public String directionTag;
    public Entity exitID;
}
