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
    private Label TurnLabel;
    private GameUtils gameUtils;

    public VBox createGameBoard(int size,GameUtils gameController){
        gameUtils = gameController;
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

                cell.setUserData(new int[]{i,j});

                cell.setOnAction(e -> handleCellClick(cell));
                gameBoard.add(cell, i, j);
            }
        }

        //Turn Label
        TurnLabel = new Label("Turn: Blue Player");
        boardBox.getChildren().addAll(gameBoard, TurnLabel);
        return boardBox;
    }

    void handleCellClick(Button cell){
        try {
            if(gameUtils.isGameOver()) return;
            int[] pos = (int[]) cell.getUserData();
            gameUtils.MakeMove(pos[0], pos[1]);
            cell.setText(Character.toString(gameUtils.getCurrentPlayer().getSelectedLetter()));
            if(gameUtils.isGameOver()){
                TurnLabel.setText("Game Over! Winner: " + gameUtils.getWinner());
            }else{
                TurnLabel.setText("Turn: " + (gameUtils.getCurrentPlayer().getColor().equals("Red") ? "Red" : "Blue") + " Player");
            }
        }catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Move");
            alert.setHeaderText("Cannot make this move");
            alert.setContentText(ex.getMessage()); // shows “Board size must be between 5 and 11.”
            alert.showAndWait();
        }
    }
}
