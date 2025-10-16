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
    void handleCellClick(Button cell) {
        try {
            // Do nothing if the game is over
            if (gameUtils.isGameOver()) return;

            // Get the cell position from user data
            int[] pos = (int[]) cell.getUserData();

            // Get the letter selected by the current player
            char letterPlayed = gameUtils.getCurrentPlayer().getSelectedLetter();
            //set the color before making the move
            cell.setStyle("-fx-background-color: " + (gameUtils.getCurrentPlayer().getColor().equals("Red") ? "lightcoral" : "lightblue") + "; -fx-border-color: black;");
            // Make the move in the game logic
            gameUtils.makeMove(pos[0], pos[1]);

            // Set the button text to the letter played and update its style
            cell.setText(Character.toString(letterPlayed));


            // Update the turn label based on game state
            if (gameUtils.isGameOver()) {
                TurnLabel.setText("Game Over! Winner: " + gameUtils.getWinner());
            } else {
                TurnLabel.setText("Turn: " + (gameUtils.getCurrentPlayer().getColor().equals("Red") ? "Red" : "Blue") + " Player");
            }

        } catch (Exception ex) {
            // Show an error alert if the move is invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Move");
            alert.setHeaderText("Cannot make this move");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

}
