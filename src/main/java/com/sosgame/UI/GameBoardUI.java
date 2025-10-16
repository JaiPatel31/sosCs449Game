package com.sosgame.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.sosgame.Logic.GameUtils;

public class GameBoardUI {
    private Label TurnLabel; // Displays whose turn it is
    private GameUtils gameUtils; // Game logic controller

    // Creates and returns the game board UI
    public VBox createGameBoard(int size,GameUtils gameController){
        gameUtils = gameController;
        VBox boardBox = new VBox(); // Main container for the board
        boardBox.setAlignment(Pos.CENTER);

        // Making the board itself
        GridPane gameBoard = new GridPane(); // Grid for board cells
        gameBoard.setPadding(new Insets(10));
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setAlignment(Pos.CENTER);

        // Add buttons for each cell
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                Button cell = new Button(); // Cell button
                cell.setPrefSize(50, 50); // Set cell size
                cell.setStyle("-fx-background-color: white; -fx-border-color: black;"); // Cell style

                cell.setUserData(new int[]{i,j}); // Store cell position

                cell.setOnAction(e -> handleCellClick(cell)); // Handle cell click
                gameBoard.add(cell, i, j); // Add cell to grid
            }
        }

        // Turn Label
        TurnLabel = new Label("Turn: Blue Player"); // Shows current turn
        boardBox.getChildren().addAll(gameBoard, TurnLabel); // Add board and label to container
        return boardBox;
    }

    // Handles a cell click event
    void handleCellClick(Button cell){
        try {
            if(gameUtils.isGameOver()) return; // Do nothing if game is over
            int[] pos = (int[]) cell.getUserData(); // Get cell position
            gameUtils.MakeMove(pos[0], pos[1]); // Make move in logic
            cell.setText(Character.toString(gameUtils.getCurrentPlayer().getSelectedLetter())); // Show letter in cell
            if(gameUtils.isGameOver()){
                TurnLabel.setText("Game Over! Winner: " + gameUtils.getWinner()); // Show winner
            }else{
                TurnLabel.setText("Turn: " + (gameUtils.getCurrentPlayer().getColor().equals("Red") ? "Red" : "Blue") + " Player"); // Update turn label
            }
        }catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR); // Show error alert
            alert.setTitle("Invalid Move");
            alert.setHeaderText("Cannot make this move");
            alert.setContentText(ex.getMessage()); // Show error message
            alert.showAndWait();
        }
    }
}
