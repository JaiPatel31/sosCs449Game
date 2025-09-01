package com.sosgame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class gameUI {

    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setTop(createTopMenu());
        root.setLeft(createLeftMenu());
        root.setRight(createRightMenu());
        root.setCenter(createGameBoard(8));
        return  root;

    }

    public HBox createTopMenu(){

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

    public VBox createLeftMenu() {
        VBox leftMenu = new VBox(20);
        leftMenu.setPadding(new Insets(10));

        //Red Blue Player Labels
        Label bluePlayer = new Label("Blue Player");

        //Human and Computer Radio Buttons
        RadioButton humanBlue = new RadioButton("Human");
        RadioButton computerBlue = new RadioButton("Computer");
        ToggleGroup blueGroup = new ToggleGroup();
        humanBlue.setToggleGroup(blueGroup);
        computerBlue.setToggleGroup(blueGroup);
        humanBlue.setSelected(true);//default value

        //Human Player Letter Selection
        RadioButton blueS = new RadioButton("S");
        RadioButton blueO = new RadioButton("O");
        ToggleGroup blueLetterGroup = new ToggleGroup();
        blueS.setToggleGroup(blueLetterGroup);
        blueO.setToggleGroup(blueLetterGroup);
        blueS.setSelected(true);//default value

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
        CheckBox recordGame = new CheckBox("Record Game");

        //Spacer to make the radio buttons go to the middle
        Region leftTopSpacer = new Region();
        Region leftBottomSpacer = new Region();
        VBox.setVgrow(leftTopSpacer, Priority.ALWAYS);
        VBox.setVgrow(leftBottomSpacer, Priority.ALWAYS);

        //add everything to the right menu
        leftMenu.getChildren().addAll(leftTopSpacer, bluePlayer, humanBlue, blueLetterSelection, computerBlue, leftBottomSpacer, recordGame);

        return leftMenu;
    }
    public VBox createRightMenu() {
        VBox rightMenu = new VBox(20);
        rightMenu.setPadding(new Insets(10));

        //Red Blue Player Labels
        Label redPlayer = new Label("Red Player");

        //Human and Computer Radio Buttons
        RadioButton humanRed = new RadioButton("Human");
        RadioButton computerRed = new RadioButton("Computer");
        ToggleGroup redGroup = new ToggleGroup();
        humanRed.setToggleGroup(redGroup);
        computerRed.setToggleGroup(redGroup);
        humanRed.setSelected(true);//default value

        //Human Player Letter Selection
        RadioButton redS = new RadioButton("S");
        RadioButton redO = new RadioButton("O");
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
        VBox blueLetterSelection = new VBox(10, redS, redO);
        blueLetterSelection.setPadding(new Insets(0, 0, 0, 20));

        //Add a Record CheckBox at the bottom
        Button replayGame = new Button("Replay Game");
        replayGame.setMaxWidth(Double.MAX_VALUE);
        Button newGame = new Button("New Game");
        newGame.setMaxWidth(Double.MAX_VALUE);
        VBox rightButtonBox = new VBox(10, replayGame, newGame);

        //Spacer to make the radio buttons go to the middle
        Region rightTopSpacer = new Region();
        Region rightBottomSpacer = new Region();
        VBox.setVgrow(rightTopSpacer, Priority.ALWAYS);
        VBox.setVgrow(rightBottomSpacer, Priority.ALWAYS);

        //add everything to the right menu
        rightMenu.getChildren().addAll(rightTopSpacer, redPlayer, humanRed, blueLetterSelection, computerRed, rightBottomSpacer, rightButtonBox);

        return rightMenu;
    }

    public VBox createGameBoard(int size){
        VBox boardBox = new VBox();
        boardBox.setAlignment(Pos.CENTER);

        //making the board itself
        GridPane gameBoard = new GridPane();
        gameBoard.setPadding(new Insets(10));
        gameBoard.setHgap(5);
        gameBoard.setVgap(5);


        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                Button cell = new Button();
                cell.setPrefSize(50, 50);

                cell.setStyle("-fx-background-color: white; -fx-border-color: black;");

                gameBoard.add(cell, j, i);
            }
        }

        //Turn Label
        Label turnLabel = new Label("Turn: Red Player or Blue Player");
        boardBox.getChildren().addAll(gameBoard, turnLabel);
        return boardBox;
    }
}
