/**
 * The class SystemManager defines the order and which
 * systems are used by the EcsManager
 */
public class SystemManager extends ISystemManager {
    /**
     * This method is executed in a threadPool.
     * All systems are run asynchronously.
     */
    public void run() {
        while (true) {
            // Manage all out dialogs and write them on output stream
            SCliDialogOutput.update();

            // Starts an input scan to players who got one
            SCliPlayerInput.update();

            // Get inputs from all brainless computers.
            SComputerInput.update();

            // Parse the input scanned
            SInputParser.update();

            // Check if command are valid
            SCliCommandCheck.update();

            // Execute commands
            SCliCommandBroker.update();
        }
    }
}
