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
    private RadioButton simpleMode;
    private RadioButton generalMode;
    private TextField boardSizeInput;
    private GameUtils gameUtils;

    public  Node createTopMenu(GameUtils gameController) {
    gameUtils = gameController;
        //Top Bar
        HBox topMenu = new HBox(20);
        topMenu.setPadding(new Insets(10));

        //Left Side Title
        Label title = new Label("SOS");

        //Middle Buttons for simple and general mode
        simpleMode = new RadioButton("Simple Mode");
        generalMode = new RadioButton("General Mode");
        ToggleGroup modeGroup = new ToggleGroup();
        simpleMode.setToggleGroup(modeGroup);
        generalMode.setToggleGroup(modeGroup);

        //Right Side for Board Size textfield
        Label boardSizeLabel = new Label("Board Size");
        boardSizeInput = new TextField();
        boardSizeInput.setText("8");//default Value
        boardSizeInput.setMaxWidth(60);

        //Spacer to push right side to the right and left side to the left
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        //Now add everything to the top bar
        topMenu.getChildren().addAll(title, spacer1, simpleMode, generalMode, spacer2, boardSizeLabel, boardSizeInput);
        return  topMenu;
    }
    //getters for the selected options
    public boolean isSimpleModeSelected() {
        return simpleMode.isSelected();
    }
    public boolean isGeneralModeSelected() {
        return generalMode.isSelected();
    }
    public String getBoardSize() {
        return boardSizeInput.getText();
    }
}
