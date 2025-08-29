package com.sosgame;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class gameUI {

    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setTop(createTopMenu());

        return  root;
    }

    public Node createTopMenu(){

        //Top Bar
        HBox topMenu = new HBox(20);
        topMenu.setPadding(new Insets(10));

        //Left Side Title
        Label title = new Label("SOS");

        //Middle Buttons for simple and general mode
        RadioButton simpleMode = new RadioButton("Simple Mode");
        RadioButton generalMode = new RadioButton("General Mode");
        ToggleGroup modeGroup = new ToggleGroup();
        simpleMode.setToggleGroup(modeGroup);
        generalMode.setToggleGroup(modeGroup);

        //Right Side for Board Size textfield
        Label boardSizeLabel = new Label("Board Size");
        TextField boardSizeInput = new TextField();
        boardSizeInput.setText("8");//default Value
        boardSizeInput.setMaxWidth(60);

        //Spacer to push right side to the right and left side to the left
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        //Now add everything to the top bar
        topMenu.getChildren().addAll(title, spacer1, simpleMode, generalMode, spacer2, boardSizeLabel, boardSizeInput);
        return topMenu;
    }


}
