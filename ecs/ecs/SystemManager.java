public class SystemManager implements Runnable {
    public SystemManager() {
        stop = false;
    }

    private void update() {
        // Write all dialogs on output stream
        SDialogOutput.update();

        // Starts an input scan
        SPlayerInput.update();

        // Parse Input
        SInputParser.update();

        // Check if command are valid
        SCommandCheck.update();

        // Execute commands
        SCommandBroker.update();
    }

    public void run() {
        Clock clock = new Clock();

        while (!this.stop) {
            if (clock.getDuration() > 300) {
                this.update();
                clock.restart();
            }
        }
    }

    public void setStop() { this.stop = true; }

    private boolean stop;
}
