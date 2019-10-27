/**
 * The class EntityGenerator provides a way
 * to generate unique ID on a very wide range.
 */
public class EntityGenerator {
    private EntityGenerator() {};

    /**
     * Get the static instance
     * @return a static instance of the generator
     */
    public static EntityGenerator getInstance() {
        if (Instance != null)
            return Instance;
        return new EntityGenerator();
    }

    /**
     * Generate a unique ID
     * @return the unique ID
     */
    public long generateID() {
        return uniqueID++;
    }

    private static EntityGenerator Instance;
    private static long uniqueID;
}


