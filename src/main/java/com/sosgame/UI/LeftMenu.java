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
    private RadioButton humanBlue;
    private RadioButton computerBlue;
    private RadioButton blueS;
    private RadioButton blueO;
    private CheckBox recordGame;
    private GameUtils gameUtils;

    public VBox createLeftMenu(GameUtils gameController) {
        this.gameUtils = gameController;
        VBox leftMenu = new VBox(20);
        leftMenu.setPadding(new Insets(10));

        //Red Blue Player Labels
        Label bluePlayer = new Label("Blue Player");

        //Human and Computer Radio Buttons
        humanBlue = new RadioButton("Human");
        computerBlue = new RadioButton("Computer");
        ToggleGroup blueGroup = new ToggleGroup();
        humanBlue.setToggleGroup(blueGroup);
        computerBlue.setToggleGroup(blueGroup);
        humanBlue.setSelected(true);//default value

        //Human Player Letter Selection
        blueS = new RadioButton("S");
        blueO = new RadioButton("O");
        ToggleGroup blueLetterGroup = new ToggleGroup();
        blueS.setToggleGroup(blueLetterGroup);
        blueO.setToggleGroup(blueLetterGroup);
        blueS.setSelected(true);//default value
        //action listeners for radio buttons
        blueO.setOnAction(e->changeSelectedLetter());
        blueS.setOnAction(e->changeSelectedLetter());

        //When Human is selected enable letter selection, otherwise disable it
        humanBlue.setOnAction(e -> {
            blueS.setDisable(false);
            blueO.setDisable(false);
        });
        computerBlue.setOnAction(e -> {
            blueS.setDisable(true);
            blueO.setDisable(true);
        });

        //Vbox for Human Letter Selection
        VBox blueLetterSelection = new VBox(10, blueS, blueO);
        blueLetterSelection.setPadding(new Insets(0, 0, 0, 20));

        //Add a Record CheckBox at the bottom
        recordGame = new CheckBox("Record Game");

        //Spacer to make the radio buttons go to the middle
        Region leftTopSpacer = new Region();
        Region leftBottomSpacer = new Region();
        VBox.setVgrow(leftTopSpacer, Priority.ALWAYS);
        VBox.setVgrow(leftBottomSpacer, Priority.ALWAYS);

        //add everything to the right menu
        leftMenu.getChildren().addAll(leftTopSpacer, bluePlayer, humanBlue, blueLetterSelection, computerBlue, leftBottomSpacer, recordGame);

        return leftMenu;
    }

    //getters for the selected options
    public String getBlueType() {
        return humanBlue.isSelected() ? "Human" : "Computer";
    }

    public char getBlueLetter() {
        return blueS.isSelected() ? 'S' : 'O';
    }

    public boolean isRecordingEnabled() {
        return recordGame.isSelected();
    }

    private void changeSelectedLetter(){
        if(blueS.isSelected()){
            gameUtils.getPlayerBlue().setSelectedLetter('S');
        }else if(blueO.isSelected()){
            gameUtils.getPlayerBlue().setSelectedLetter('O');
        }
    }

}
