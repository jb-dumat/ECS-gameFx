package com.mycompany.mavenproject1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import core.GameCore;
import javafx.beans.value.ObservableValue;
import systems.SGuiPlayerInput;

public class FXMLController implements Initializable {

    @FXML
    public TextArea consoleBox = null;

    @FXML
    public TextArea playerName = null;
    
    @FXML
    public TextArea roomName = null;
    
    @FXML
    public ListView<String> playerInventory = null;
    
    @FXML
    public ListView<String> roomInventory = null;
        
    @FXML
    public ListView<String> roomPlayers = null;
    
    @FXML
    public Button btnNorth = null;
    
    @FXML
    public Button btnEast = null;
        
    @FXML
    public Button btnWest = null;
            
    @FXML
    public Button btnSouth = null;
    
    @FXML
    private void handleGoNorth(ActionEvent event) {
        SGuiPlayerInput.putInput("go north");
    }
    
    @FXML
    private void handleGoSouth(ActionEvent event) {
        SGuiPlayerInput.putInput("go south");
    }
    
    @FXML
    private void handleGoEast(ActionEvent event) {
        SGuiPlayerInput.putInput("go east");
    }
    
    @FXML
    private void handleGoWest(ActionEvent event) {
        SGuiPlayerInput.putInput("go west");
    }
    
    @FXML
    private void handleTake(ActionEvent event) {
        SGuiPlayerInput.putInput("take " + roomInvSelection);
    }
    
    @FXML
    private void handleDrop(ActionEvent event) {
        SGuiPlayerInput.putInput("drop " + playerInvSelection);
    }
    
    @FXML
    private void handleGive(ActionEvent event) {
        String InvSelection = "";
        if (playerInvSelection != null) {
            InvSelection = playerInvSelection.split("\\s+")[0];
        }

        SGuiPlayerInput.putInput("give " + InvSelection + " " + roomPlayersSelection);
    }
    
    @FXML
    private void handleLook(ActionEvent event) {
        SGuiPlayerInput.putInput("look");
    }
    
    @FXML
    private void handleHelp(ActionEvent event) {
        SGuiPlayerInput.putInput("help");
    }
    
    @FXML
    private void handleLoadFromFile(ActionEvent event) {
        pathToFile = ((MenuItem)event.getSource()).getText();
        
        GameCore.getInstance().initializeGameFromFile();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.pathToFile = "Default";
        this.playerInvSelection = "";
        this.roomInvSelection = "";
        this.roomPlayersSelection = "";
        
        playerInventory.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            playerInvSelection = newValue;
        });
        
        roomInventory.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            roomInvSelection = newValue;
        });
        
        roomPlayers.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            roomPlayersSelection = newValue;
        });
    }
    
    /**
     * Getter
     * @return
     */
    public String getPlayerInvSelection() {
        return this.playerInvSelection;
    }
    
    /**
     * Setter
     * @param text
     */
    public void setPlayerInvSelection(String text) {
        this.playerInvSelection = text;
    }
    
        /**
     * Getter
     * @return
     */
    public String getRoomInvSelection() {
        return this.roomInvSelection;
    }
    
    /**
     * Setter
     * @param text
     */
    public void setRoomInvSelection(String text) {
        this.roomInvSelection = text;
    }

        /**
     * Getter
     * @return
     */
    public String getRoomPlayersSelection() {
        return this.roomPlayersSelection;
    }
    
    /**
     * Setter
     * @param text
     */
    public void setRoomPlayersSelection(String text) {
        this.playerInvSelection = text;
    }
    
        /**
     * Getter
     * @return
     */
    public String getPathToFile() {
        return this.pathToFile;
    }
    
    /**
     * Setter
     * @param text
     */
    public void setPathToFile(String text) {
        this.pathToFile = text;
    }

    private String pathToFile;
    private String playerInvSelection;
    private String roomInvSelection;
    private String roomPlayersSelection;
}
