package com.sosgame.UI;

import com.sosgame.Logic.GameUtils;
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
    private GameUtils gameUtils; // Game logic controller

    // Creates the top menu bar UI
    public  Node createTopMenu(GameUtils gameController) {
        this.gameUtils = gameController;
        initializeModeControls();
        initializeBoardSizeInput();
        HBox topMenu = createTopMenuContainer();
        Label title = createTitleLabel();
        Label boardSizeLabel = new Label("Board Size");
        Region spacerLeft = createSpacer();
        Region spacerRight = createSpacer();
        assembleTopMenu(topMenu, title, spacerLeft, spacerRight, boardSizeLabel);
        return  topMenu;
    }

    private void initializeModeControls() {
        simpleMode = new RadioButton("Simple Mode");
        generalMode = new RadioButton("General Mode");
        ToggleGroup modeGroup = new ToggleGroup();
        simpleMode.setToggleGroup(modeGroup);
        generalMode.setToggleGroup(modeGroup);
        simpleMode.setSelected(true);
    }

    private void initializeBoardSizeInput() {
        boardSizeInput = new TextField();
        boardSizeInput.setText("8");
        boardSizeInput.setMaxWidth(60);
    }

    private HBox createTopMenuContainer() {
        HBox topMenu = new HBox(20);
        topMenu.setPadding(new Insets(10));
        return topMenu;
    }

    private Label createTitleLabel() {
        return new Label("SOS");
    }

    private Region createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    private void assembleTopMenu(HBox topMenu, Label title, Region spacerLeft, Region spacerRight, Label boardSizeLabel) {
        topMenu.getChildren().addAll(
            title,
            spacerLeft,
            simpleMode,
            generalMode,
            spacerRight,
            boardSizeLabel,
            boardSizeInput
        );
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
