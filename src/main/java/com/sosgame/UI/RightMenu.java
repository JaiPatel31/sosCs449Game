package com.sosgame.UI;

import com.sosgame.Logic.Game;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;

public class RightMenu {
    private RadioButton humanRed = new RadioButton("Human"); // Radio button for Human player
    private RadioButton computerRed = new RadioButton("Computer"); // Radio button for Computer player
    private RadioButton redS = new RadioButton("S"); // Radio button for letter S
    private RadioButton redO = new RadioButton("O"); // Radio button for letter O
    private Button replayGame = new Button("Replay Game"); // Button to replay the game
    private Button newGame = new Button("New Game"); // Button to start a new game
    private Game game; // Game logic controller


    // Creates the right menu UI for Red player options
    public VBox createRightMenu(Game gameController, gameUI gameUI) {
        this.game = gameController;


        VBox rightMenu = new VBox(20); // Main container for right menu
        rightMenu.setPadding(new Insets(10));

        // Red Player Label
        Label redPlayer = new Label("Red Player"); // Label for Red player

        // Human and Computer Radio Buttons
        humanRed = new RadioButton("Human"); // Human player option
        computerRed = new RadioButton("Computer"); // Computer player option
        ToggleGroup redGroup = new ToggleGroup(); // Group for player type
        humanRed.setToggleGroup(redGroup);
        computerRed.setToggleGroup(redGroup);
        humanRed.setSelected(true); // Default value: Human selected

        // Human Player Letter Selection
        redS = new RadioButton("S"); // Option for letter S
        redO = new RadioButton("O"); // Option for letter O
        ToggleGroup redLetterGroup = new ToggleGroup(); // Group for letter selection
        redS.setToggleGroup(redLetterGroup);
        redO.setToggleGroup(redLetterGroup);
        redS.setSelected(true); // Default value: S selected
        // Action listeners for radio buttons
        redO.setOnAction(e->changeRedSelectedLetter()); // Change letter to O
        redS.setOnAction(e->changeRedSelectedLetter()); // Change letter to S
        // When Human is selected enable letter selection, otherwise disable it
        humanRed.setOnAction(e -> {
            redS.setDisable(false); // Enable S
            redO.setDisable(false); // Enable O
        });
        computerRed.setOnAction(e -> {
            redS.setDisable(true); // Disable S
            redO.setDisable(true); // Disable O
        });

        // VBox for Human Letter Selection
        VBox redLetterSelection = new VBox(10, redS, redO); // Container for letter options
        redLetterSelection.setPadding(new Insets(0, 0, 0, 20));

        // Replay and New Game Buttons
        replayGame = new Button("Replay Game"); // Button to replay game
        replayGame.setMaxWidth(Double.MAX_VALUE);
        newGame = new Button("New Game"); // Button to start new game
        newGame.setMaxWidth(Double.MAX_VALUE);
        VBox rightButtonBox = new VBox(10, replayGame, newGame); // Container for buttons
        newGame.setOnAction(e -> gameUI.startNewGame()); // Start new game on click

        // Spacer to make the radio buttons go to the middle
        Region rightTopSpacer = new Region(); // Top spacer
        Region rightBottomSpacer = new Region(); // Bottom spacer
        VBox.setVgrow(rightTopSpacer, Priority.ALWAYS);
        VBox.setVgrow(rightBottomSpacer, Priority.ALWAYS);

        // Add everything to the right menu
        rightMenu.getChildren().addAll(rightTopSpacer, redPlayer, humanRed, redLetterSelection, computerRed, rightBottomSpacer, rightButtonBox);

        return rightMenu;
    }
    // Getter for the selected player type (Human or Computer)
   public String getRedType(){
        return humanRed.isSelected() ? "Human" : "Computer";
   }
   // Changes the selected letter for Red player
    private void changeRedSelectedLetter(){
        if(redS.isSelected()){
            game.getPlayerRed().setSelectedLetter('S');
        }else if(redO.isSelected()){
            game.getPlayerRed().setSelectedLetter('O');
        }
    }

}
