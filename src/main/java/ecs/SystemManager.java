package ecs;

import systems.SCommandBroker;
import systems.SCommandCheck;
import systems.SComputerInput;
import systems.SDialogOutputCleaner;
import systems.SGuiDialogOutput;
import systems.SGuiExitBtn;
import systems.SGuiNames;
import systems.SGuiPlayerInput;
import systems.SGuiPlayerInventory;
import systems.SGuiRoomInventory;
import systems.SGuiRoomPlayers;
import systems.SInputParser;
import utils.Clock;

/**
 * The class SystemManager defines the order and which
 * systems are used by the EcsManager
 */
public class SystemManager extends ISystemManager {
    public SystemManager() { 
        this.running = false; 
        this.clock = new Clock();
    }
    
    /**
     * This method is executed in a threadPool.
     * All systems are run asynchronously.
     */
    public void run() {
        this.running = true;
        clock.restart();

        while (running) {
            if (clock.getDuration() >= 30) {
                // Update player and position names.
                SGuiNames.update();

                // Manage all out dialogs and write them on output stream
                SGuiDialogOutput.update();            

                // Update the player's inventory.
                SGuiPlayerInventory.update();

                // Update the room's inventory.
                SGuiRoomInventory.update();

                // Update the room's player list.
                SGuiRoomPlayers.update();

                // Remove all output dialogs.
                SDialogOutputCleaner.update();

                // Disable/Enable exit buttons.
                SGuiExitBtn.update();

                // --->
                // Starts an input scan to players who got one
                SGuiPlayerInput.update();            

                if (!this.running)
                    break;
                                
                // Get inputs from all brainless computers.
                SComputerInput.update();

                // Parse the input scanned
                SInputParser.update();

                // Check if command are valid
                SCommandCheck.update();

                // Execute commands
                SCommandBroker.update();
                                
                clock.restart();
            }
        }
    }
    
    @Override
    public void setStop() {
        this.running = false;
    }
    
    private final Clock clock;
    private boolean running;
}
