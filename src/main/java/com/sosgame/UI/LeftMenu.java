package com.sosgame.UI;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import com.sosgame.Logic.GameUtils;

public class LeftMenu {
    private RadioButton humanBlue; // Radio button for Human player
    private RadioButton computerBlue; // Radio button for Computer player
    private RadioButton blueS; // Radio button for letter S
    private RadioButton blueO; // Radio button for letter O
    private CheckBox recordGame; // Checkbox to record the game
    private GameUtils gameUtils; // Game logic controller

    // Creates the left menu UI for Blue player options
    public VBox createLeftMenu(GameUtils gameController) {
        this.gameUtils = gameController;
        VBox leftMenu = new VBox(20); // Main container for left menu
        leftMenu.setPadding(new Insets(10));

        // Red Blue Player Labels
        Label bluePlayer = new Label("Blue Player"); // Label for Blue player

        // Human and Computer Radio Buttons
        humanBlue = new RadioButton("Human"); // Human player option
        computerBlue = new RadioButton("Computer"); // Computer player option
        ToggleGroup blueGroup = new ToggleGroup(); // Group for player type
        humanBlue.setToggleGroup(blueGroup);
        computerBlue.setToggleGroup(blueGroup);
        humanBlue.setSelected(true); // Default value: Human selected

        // Human Player Letter Selection
        blueS = new RadioButton("S"); // Option for letter S
        blueO = new RadioButton("O"); // Option for letter O
        ToggleGroup blueLetterGroup = new ToggleGroup(); // Group for letter selection
        blueS.setToggleGroup(blueLetterGroup);
        blueO.setToggleGroup(blueLetterGroup);
        blueS.setSelected(true); // Default value: S selected
        // Action listeners for radio buttons
        blueO.setOnAction(e->changeBlueSelectedLetter()); // Change letter to O
        blueS.setOnAction(e->changeBlueSelectedLetter()); // Change letter to S

        // When Human is selected enable letter selection, otherwise disable it
        humanBlue.setOnAction(e -> {
            blueS.setDisable(false); // Enable S
            blueO.setDisable(false); // Enable O
            changeBlueSelectedLetter();
        });
        computerBlue.setOnAction(e -> {
            blueS.setDisable(true); // Disable S
            blueO.setDisable(true); // Disable O
        });

        // VBox for Human Letter Selection
        VBox blueLetterSelection = new VBox(10, blueS, blueO); // Container for letter options
        blueLetterSelection.setPadding(new Insets(0, 0, 0, 20));

        // Add a Record CheckBox at the bottom
        recordGame = new CheckBox("Record Game"); // Checkbox to record game

        // Spacer to make the radio buttons go to the middle
        Region leftTopSpacer = new Region(); // Top spacer
        Region leftBottomSpacer = new Region(); // Bottom spacer
        VBox.setVgrow(leftTopSpacer, Priority.ALWAYS);
        VBox.setVgrow(leftBottomSpacer, Priority.ALWAYS);

        // Add everything to the left menu
        leftMenu.getChildren().addAll(leftTopSpacer, bluePlayer, humanBlue, blueLetterSelection, computerBlue, leftBottomSpacer, recordGame);

        return leftMenu;
    }

    // Getter for the selected player type (Human or Computer)
    public String getBlueType() {
        return humanBlue.isSelected() ? "Human" : "Computer";
    }

    // Changes the selected letter for Blue player
    private void changeBlueSelectedLetter(){
        if(blueS.isSelected()){
            gameUtils.getPlayerBlue().setSelectedLetter('S');
        }else if(blueO.isSelected()){
            gameUtils.getPlayerBlue().setSelectedLetter('O');
        }
    }

}
