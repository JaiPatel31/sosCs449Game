package com.sosgame.UI;

import com.sosgame.Logic.GameUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;

public class RightMenu {
    private RadioButton humanRed; // Radio button for Human player
    private RadioButton computerRed; // Radio button for Computer player
    private RadioButton redS; // Radio button for letter S
    private RadioButton redO; // Radio button for letter O
    private Button replayGame; // Button to replay the game
    private Button newGame; // Button to start a new game
    private GameUtils gameUtils; // Game logic controller

    // Creates the right menu UI for Red player options
    public VBox createRightMenu(GameUtils gameController, GameUI gameUI) {
        this.gameUtils = gameController;
        initializePlayerControls();
        initializeLetterControls();
        initializeActionButtons(gameUI);
        VBox letterSelectionBox = buildLetterSelectionBox();
        VBox buttonBox = buildButtonBox();
        VBox rightMenu = assembleRightMenu(letterSelectionBox, buttonBox);
        wireEventHandlers();
        return rightMenu;
    }

    private void initializePlayerControls() {
        humanRed = new RadioButton("Human");
        computerRed = new RadioButton("Computer");
        ToggleGroup redGroup = new ToggleGroup();
        humanRed.setToggleGroup(redGroup);
        computerRed.setToggleGroup(redGroup);
        humanRed.setSelected(true);
    }

    private void initializeLetterControls() {
        redS = new RadioButton("S");
        redO = new RadioButton("O");
        ToggleGroup redLetterGroup = new ToggleGroup();
        redS.setToggleGroup(redLetterGroup);
        redO.setToggleGroup(redLetterGroup);
        redS.setSelected(true);
    }

    private void initializeActionButtons(GameUI gameUI) {
        replayGame = new Button("Replay Game");
        replayGame.setMaxWidth(Double.MAX_VALUE);
        replayGame.setOnAction(e -> gameUI.onReplayGame());

        newGame = new Button("New Game");
        newGame.setMaxWidth(Double.MAX_VALUE);
        newGame.setOnAction(e -> gameUI.startNewGame());
    }

    private VBox buildLetterSelectionBox() {
        VBox box = new VBox(10, redS, redO);
        box.setPadding(new Insets(0, 0, 0, 20));
        return box;
    }

    private VBox buildButtonBox() {
        return new VBox(10, replayGame, newGame);
    }

    private VBox assembleRightMenu(VBox letterSelectionBox, VBox buttonBox) {
        VBox rightMenu = new VBox(20);
        rightMenu.setPadding(new Insets(10));

        Label redPlayer = new Label("Red Player");
        Region rightTopSpacer = new Region();
        Region rightBottomSpacer = new Region();
        VBox.setVgrow(rightTopSpacer, Priority.ALWAYS);
        VBox.setVgrow(rightBottomSpacer, Priority.ALWAYS);

        rightMenu.getChildren().addAll(
            rightTopSpacer,
            redPlayer,
            humanRed,
            letterSelectionBox,
            computerRed,
            rightBottomSpacer,
            buttonBox
        );
        return rightMenu;
    }

    private void wireEventHandlers() {
        redS.setOnAction(e -> changeRedSelectedLetter());
        redO.setOnAction(e -> changeRedSelectedLetter());

        humanRed.setOnAction(e -> {
            redS.setDisable(false);
            redO.setDisable(false);
            changeRedSelectedLetter();
        });
        computerRed.setOnAction(e -> {
            redS.setDisable(true);
            redO.setDisable(true);
        });
    }
    // Getter for the selected player type (Human or Computer)
   public String getRedType(){
        return humanRed.isSelected() ? "Human" : "Computer";
   }
   // Changes the selected letter for Red player
    private void changeRedSelectedLetter(){
        if(redS.isSelected()){
            gameUtils.getPlayerRed().setSelectedLetter('S');
        }else if(redO.isSelected()){
            gameUtils.getPlayerRed().setSelectedLetter('O');
        }
    }

}
