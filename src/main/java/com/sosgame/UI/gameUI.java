package com.sosgame.UI;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import com.sosgame.Logic.GameUtils;

public class gameUI {
    private TopMenu topMenu; // Top menu bar
    private LeftMenu leftMenu; // Left menu panel
    private RightMenu rightMenu; // Right menu panel
    private GameBoardUI gameBoardUI; // Game board UI
    private BorderPane root; // Main layout container
    private GameUtils gameUtils; // Game logic controller

    // Creates the main GUI and returns the root layout
    public BorderPane createGUI(){
        gameUtils = new GameUtils(); // Initialize game logic
        root = new BorderPane(); // Create main layout

        topMenu = new TopMenu(); // Create top menu
        root.setTop(topMenu.createTopMenu(gameUtils)); // Add top menu

        leftMenu = new LeftMenu(); // Create left menu
        root.setLeft(leftMenu.createLeftMenu(gameUtils)); // Add left menu

        rightMenu = new RightMenu(); // Create right menu
        root.setRight(rightMenu.createRightMenu(gameUtils,this)); // Add right menu

        gameBoardUI = new GameBoardUI(); // Create game board UI
        startNewGame(); // Start a new game

        return  root; // Return the main layout
    }

    // Starts a new game, checks for valid board size
    public void startNewGame(){
        try {
            validSize(); // Try to start with user size
        }
        catch(IllegalArgumentException ex){
            illegalSize(ex); // If invalid, use default size
        }
    }

    // Handles invalid board size, starts game with default size
    private void illegalSize(IllegalArgumentException ex){
        gameUtils.startNewGame(5, topMenu.getMode(),rightMenu.getRedType(), leftMenu.getBlueType(), gameBoardUI,leftMenu.isRecordGame()); // Default size 5

        resetGame(5);
        Alert alert = new Alert(Alert.AlertType.ERROR); // Show error alert
        alert.setTitle("Invalid Settings");
        alert.setHeaderText("Started Game with default value 5");
        alert.setContentText(ex.getMessage()); // shows “Board size must be between 5 and 11.”
        alert.showAndWait();
    }

    // Handles valid board size, starts game with user size
    private void validSize(){
        int size = topMenu.getBoardSize();
        gameUtils.startNewGame(size, topMenu.getMode(),rightMenu.getRedType(), leftMenu.getBlueType(), gameBoardUI, leftMenu.isRecordGame()); // Start game
        resetGame(size);
    }

    private void resetGame(int size){
        root.setCenter(null); // Clear center
        root.setCenter(gameBoardUI.createGameBoard(size, gameUtils)); // Add new board

    }
 }
