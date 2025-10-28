package com.sosgame.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import com.sosgame.Logic.GameUtils;
import com.sosgame.Logic.Player;
import com.sosgame.Logic.Game;

import java.util.List;

public class GameBoardUI {
    private Label turnLabel;
    private GameUtils gameUtils;
    private GridPane boardGrid;
    private Pane lineLayer; //transparent overlay for drawing lines

    public VBox createGameBoard(int size, GameUtils gameController) {
        this.gameUtils = gameController;

        VBox boardBox = new VBox(10);
        boardBox.setAlignment(Pos.CENTER);

        boardGrid = new GridPane();
        boardGrid.setPadding(new Insets(10));
        boardGrid.setHgap(5);
        boardGrid.setVgap(5);
        boardGrid.setAlignment(Pos.CENTER);

        //Create grid buttons
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button cell = new Button();
                cell.setPrefSize(50, 50);
                cell.setStyle("-fx-background-color: white; -fx-border-color: black;");
                cell.setUserData(new int[]{i, j});
                cell.setOnAction(e -> handleCellClick(cell));
                boardGrid.add(cell, j, i);
            }
        }

        //Transparent Pane for SOS lines
        lineLayer = new Pane();
        lineLayer.setPickOnBounds(false);

        // Stack the grid and line layer
        StackPane stack = new StackPane(boardGrid, lineLayer);

        turnLabel = new Label("Turn: Blue Player");
        boardBox.getChildren().addAll(stack, turnLabel);

        return boardBox;
    }

    private void handleCellClick(Button cell) {
        if (gameUtils.isGameOver()) return;

        int[] pos = (int[]) cell.getUserData();
        gameUtils.makeMove(pos[0], pos[1]);

        refreshBoard();
        refreshTurnLabel();
    }

    private void refreshBoard() {
        for (Node node : boardGrid.getChildren()) {
            if (node instanceof Button cell) {
                int[] pos = (int[]) cell.getUserData();
                char letter = gameUtils.getGameBoard().getLetterAt(pos[0], pos[1]);
                char owner = gameUtils.getGameBoard().getOwnerAt(pos[0], pos[1]);
                cell.setText(letter == '\0' ? "" : String.valueOf(letter));
                cell.setStyle("-fx-background-color: " +
                        (owner == 'R' ? "lightcoral"
                                : owner == 'B' ? "lightblue"
                                : "white") +
                        "; -fx-border-color: black;");
            }
        }

        drawSOSLines(); //draw connecting lines
    }

    private void refreshTurnLabel() {
        if (gameUtils.isGameOver()) {
            Player w = gameUtils.getWinner();
            turnLabel.setText(w != null
                    ? w.getColor() + " Player Wins!"
                    : "Draw!");
        } else {
            turnLabel.setText("Turn: " + gameUtils.getCurrentPlayer().getColor());
        }
    }

    //Draw SOS connection lines
    private void drawSOSLines() {
        lineLayer.getChildren().clear();

        List<Game.SOSLine> sosLines = gameUtils.getCompletedSOSLines();
        if (sosLines == null) return;

        for (Game.SOSLine line : sosLines) {
            Node startNode = getCell(line.startRow, line.startCol);
            Node endNode = getCell(line.endRow, line.endCol);
            if (startNode == null || endNode == null) continue;

            double startX = startNode.getLayoutX() + startNode.getBoundsInParent().getWidth() / 2;
            double startY = startNode.getLayoutY() + startNode.getBoundsInParent().getHeight() / 2;
            double endX = endNode.getLayoutX() + endNode.getBoundsInParent().getWidth() / 2;
            double endY = endNode.getLayoutY() + endNode.getBoundsInParent().getHeight() / 2;

            Color color = line.color.equalsIgnoreCase("Red") ? Color.RED : Color.BLUE;

            Line sosLine = new Line(startX, startY, endX, endY);
            sosLine.setStroke(color);
            sosLine.setStrokeWidth(4);
            sosLine.setStrokeLineCap(StrokeLineCap.ROUND);

            lineLayer.getChildren().add(sosLine);
        }
    }

    private Node getCell(int row, int col) {
        for (Node node : boardGrid.getChildren()) {
            Integer r = GridPane.getRowIndex(node);
            Integer c = GridPane.getColumnIndex(node);
            if (r != null && c != null && r == row && c == col) {
                return node;
            }
        }
        return null;
    }
}

