public class EcsManager extends AEcsManager {
    public EcsManager() {
        super();
    }

    private static EcsManager Instance;

    public static EcsManager getInstance() {
        if (Instance == null) {
            Instance = new EcsManager();
            return Instance;
        }
        return Instance;
    }

    public void update() {
//        try {

            // Check if command are valid
            SCommandCheck.compute();

            // Compute movements
            SMovement1D.compute();

            // Write all dialogs on output stream
            SDialogOutput.compute();

/*        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
*/    }
}
