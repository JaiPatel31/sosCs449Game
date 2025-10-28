package com.sosgame.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.sosgame.Logic.GameUtils;
import com.sosgame.Logic.Player;

public class GameBoardUI {
    private Label turnLabel; // Displays whose turn it is
    private GameUtils gameUtils; // Game logic controller
    private GridPane boardGrid; // Reference to the grid for refresh()

    // Creates and returns the game board UI
    public VBox createGameBoard(int size, GameUtils gameController) {
        this.gameUtils = gameController;

        VBox boardBox = new VBox(10); // Main container for the board
        boardBox.setAlignment(Pos.CENTER);

        // Make the grid for the board
        boardGrid = new GridPane();
        boardGrid.setPadding(new Insets(10));
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);
        boardGrid.setAlignment(Pos.CENTER);

        // Add buttons for each cell
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button cell = new Button();
                cell.setPrefSize(50, 50);
                cell.setStyle("-fx-background-color: white; -fx-border-color: black;");
                cell.setUserData(new int[]{i, j}); // Store row/col
                cell.setOnAction(e -> handleCellClick(cell));
                boardGrid.add(cell, j, i); // JavaFX expects (column, row)
            }
        }

        // Turn Label
        turnLabel = new Label("Turn: Blue Player");
        boardBox.getChildren().addAll(boardGrid, turnLabel);
        return boardBox;
    }

    // Called when a player clicks a cell
    void handleCellClick(Button cell) {
        if (gameUtils.isGameOver()) return;

        int[] pos = (int[]) cell.getUserData();
        gameUtils.makeMove(pos[0], pos[1]);

        refreshBoard();
        refreshTurnLabel();
    }

    // Refresh all cells from model data
    void refreshBoard() {
        for (Node node : boardGrid.getChildren()) { // ✅ use boardGrid, not this
            if (node instanceof Button cell) {
                int[] pos = (int[]) cell.getUserData();
                char letter = gameUtils.getGameBoard().getLetterAt(pos[0], pos[1]);
                char owner  = gameUtils.getGameBoard().getOwnerAt(pos[0], pos[1]);
                cell.setText(letter == '\0' ? "" : String.valueOf(letter));
                cell.setStyle("-fx-background-color: " +
                        (owner == 'R' ? "lightcoral"
                                : owner == 'B' ? "lightblue"
                                : "white") +
                        "; -fx-border-color: black;");
            }
        }
    }

    // Refresh the turn label at bottom
    void refreshTurnLabel() {
        if (gameUtils.isGameOver()) {
            Player w = gameUtils.getWinner();
            turnLabel.setText(w != null
                    ? w.getColor() + " Player Wins!"
                    : "Draw!");
        } else {
            turnLabel.setText("Turn: " + gameUtils.getCurrentPlayer().getColor());
        }
    }
}

