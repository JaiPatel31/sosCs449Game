package com.sosgame.UI;

import com.sosgame.Logic.Game;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TopMenu {
    private RadioButton simpleMode; // Radio button for Simple mode
    private RadioButton generalMode; // Radio button for General mode
    private TextField boardSizeInput; // Input field for board size
    private Game game; // Game logic controller

    // Creates the top menu bar UI
    public  Node createTopMenu(Game gameController) {
        game = gameController;
        // Top Bar container
        HBox topMenu = new HBox(20);
        topMenu.setPadding(new Insets(10));

        // Left Side Title
        Label title = new Label("SOS"); // Game title label

        // Middle Buttons for simple and general mode
        simpleMode = new RadioButton("Simple Mode"); // Simple mode option
        simpleMode.setSelected(true); // Simple mode is default
        generalMode = new RadioButton("General Mode"); // General mode option
        ToggleGroup modeGroup = new ToggleGroup(); // Group for mode selection
        simpleMode.setToggleGroup(modeGroup);
        generalMode.setToggleGroup(modeGroup);

        // Right Side for Board Size textfield
        Label boardSizeLabel = new Label("Board Size"); // Label for board size
        boardSizeInput = new TextField(); // Input for board size
        boardSizeInput.setText("8"); // Default Value
        boardSizeInput.setMaxWidth(60);

        // Spacer to push right side to the right and left side to the left
        Region spacer1 = new Region(); // Spacer for left
        Region spacer2 = new Region(); // Spacer for right
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        // Add everything to the top bar
        topMenu.getChildren().addAll(title, spacer1, simpleMode, generalMode, spacer2, boardSizeLabel, boardSizeInput);
        return  topMenu;
    }
    // Getter for the selected game mode
    public String getMode() {
        if(simpleMode.isSelected()) return "Simple";
        else return "General";
    }
    // Getter for the board size
    public int getBoardSize() {
        return Integer.parseInt(boardSizeInput.getText());
    }
}
