import java.util.List;

public class EntityGenerator {
    private EntityGenerator() {};

    public static EntityGenerator getInstance() {
        if (Instance != null)
            return Instance;
        return new EntityGenerator();
    }

    public long generateID() {
        if (uniqueID == Integer.MAX_VALUE) {
            // Check for free slot
            // return entityIdFreeSlot;
        }
        return uniqueID++;
    }

    private static EntityGenerator Instance;
    private static long uniqueID;
}


