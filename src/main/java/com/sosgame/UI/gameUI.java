package com.sosgame.UI;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import com.sosgame.Logic.Game;

public class gameUI {
    private TopMenu topMenu; // Top menu bar
    private LeftMenu leftMenu; // Left menu panel
    private RightMenu rightMenu; // Right menu panel
    private GameBoardUI gameBoardUI; // Game board UI
    private BorderPane root; // Main layout container
    private Game game; // Game logic controller

    // Creates the main GUI and returns the root layout
    public BorderPane createGUI(){
        game = new Game(); // Initialize game logic
        root = new BorderPane(); // Create main layout

        topMenu = new TopMenu(); // Create top menu
        root.setTop(topMenu.createTopMenu(game)); // Add top menu

        leftMenu = new LeftMenu(); // Create left menu
        root.setLeft(leftMenu.createLeftMenu(game)); // Add left menu

        rightMenu = new RightMenu(); // Create right menu
        root.setRight(rightMenu.createRightMenu(game,this)); // Add right menu

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
        game.startNewGame(5, topMenu.getMode(),rightMenu.getRedType(), leftMenu.getBlueType()); // Default size 5

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
        game.startNewGame(size, topMenu.getMode(),rightMenu.getRedType(), leftMenu.getBlueType()); // Start game
        resetGame(size);
    }

    private void resetGame(int size){
        root.setCenter(null); // Clear center
        root.setCenter(gameBoardUI.createGameBoard(size, game)); // Add new board
        root.setLeft(null);
        root.setRight(null);
        root.setLeft(leftMenu.createLeftMenu(game)); // Re-add left menu
        root.setRight(rightMenu.createRightMenu(game,this)); // Re-add right menu
    }
 }
