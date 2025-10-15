package com.sosgame.UI;

import com.sosgame.Logic.GameUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RightMenu {
    private RadioButton humanRed = new RadioButton("Human");
    private RadioButton computerRed = new RadioButton("Computer");
    private RadioButton redS = new RadioButton("S");
    private RadioButton redO = new RadioButton("O");
    private Button replayGame = new Button("Replay Game");
    private Button newGame = new Button("New Game");
    private GameUtils gameUtils;
    public VBox createRightMenu(GameUtils gameController) {
        this.gameUtils = gameController;
        VBox rightMenu = new VBox(20);
        rightMenu.setPadding(new Insets(10));

        //Red Blue Player Labels
        Label redPlayer = new Label("Red Player");

        //Human and Computer Radio Buttons
        humanRed = new RadioButton("Human");
        computerRed = new RadioButton("Computer");
        ToggleGroup redGroup = new ToggleGroup();
        humanRed.setToggleGroup(redGroup);
        computerRed.setToggleGroup(redGroup);
        humanRed.setSelected(true);//default value

        //Human Player Letter Selection
        redS = new RadioButton("S");
        redO = new RadioButton("O");
        ToggleGroup redLetterGroup = new ToggleGroup();
        redS.setToggleGroup(redLetterGroup);
        redO.setToggleGroup(redLetterGroup);
        redS.setSelected(true);//default value

        //When Human is selected enable letter selection, otherwise disable it
        humanRed.setOnAction(e -> {
            redS.setDisable(false);
            redO.setDisable(false);
        });
        computerRed.setOnAction(e -> {
            redS.setDisable(true);
            redO.setDisable(true);
        });

        //Vbox for Human Letter Selection
        VBox redLetterSelection = new VBox(10, redS, redO);
        redLetterSelection.setPadding(new Insets(0, 0, 0, 20));

        //Add a Record CheckBox at the bottom
        replayGame = new Button("Replay Game");
        replayGame.setMaxWidth(Double.MAX_VALUE);
        newGame = new Button("New Game");
        newGame.setMaxWidth(Double.MAX_VALUE);
        VBox rightButtonBox = new VBox(10, replayGame, newGame);

        //Spacer to make the radio buttons go to the middle
        Region rightTopSpacer = new Region();
        Region rightBottomSpacer = new Region();
        VBox.setVgrow(rightTopSpacer, Priority.ALWAYS);
        VBox.setVgrow(rightBottomSpacer, Priority.ALWAYS);

        //add everything to the right menu
        rightMenu.getChildren().addAll(rightTopSpacer, redPlayer, humanRed, redLetterSelection, computerRed, rightBottomSpacer, rightButtonBox);

        return rightMenu;
    }
   public String getRedType(){
        return humanRed.isSelected() ? "Human" : "Computer";
   }
   public String getRedLetter(){
        return redS.isSelected() ? "S" : "O";
   }
   public boolean isReplayGame(){
        return replayGame.isPressed();
   }
   public boolean isNewGame(){
        return newGame.isPressed();
    }

}
