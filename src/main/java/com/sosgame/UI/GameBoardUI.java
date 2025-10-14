package com.sosgame.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GameBoardUI {
    private Label TurnLabel;

    public VBox createGameBoard(int size){
        VBox boardBox = new VBox();
        boardBox.setAlignment(Pos.CENTER);

        //making the board itself
        GridPane gameBoard = new GridPane();
        gameBoard.setPadding(new Insets(10));
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);
        gameBoard.setAlignment(Pos.CENTER);

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                Button cell = new Button();
                cell.setPrefSize(50, 50);

                cell.setStyle("-fx-background-color: white; -fx-border-color: black;");

                gameBoard.add(cell, j, i);
            }
        }

        //Turn Label
        TurnLabel = new Label("Turn: Red Player or Blue Player");
        boardBox.getChildren().addAll(gameBoard, TurnLabel);
        return boardBox;
    }
}
