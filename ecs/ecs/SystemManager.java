public class SystemManager implements Runnable {
    public SystemManager() {
        stop = false;
    }

    private void update() {
        // Check if command are valid
        SCommandCheck.compute();

        // Compute movements
        SMovement1D.compute();

        // Write all dialogs on output stream
        SDialogOutput.compute();

        // Starts an input scan
        SInputs.compute();

        // Parse command from input
        SCommandMaker.compute();
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
