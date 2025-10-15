package com.sosgame.UI;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import com.sosgame.Logic.GameUtils;

public class gameUI {
    private TopMenu topMenu;
    private LeftMenu leftMenu;
    private RightMenu rightMenu;
    private GameBoardUI gameBoardUI;
    private BorderPane root;
    private GameUtils gameUtils;

    public BorderPane createGUI(){
        gameUtils = new GameUtils();
        root = new BorderPane();

        topMenu = new TopMenu();
        root.setTop(topMenu.createTopMenu(gameUtils));

        leftMenu = new LeftMenu();
        root.setLeft(leftMenu.createLeftMenu(gameUtils));

        rightMenu = new RightMenu();
        root.setRight(rightMenu.createRightMenu(gameUtils,this));

        gameBoardUI = new GameBoardUI();
        startNewGame();

        return  root;

    }

    //Start new game function
    public void startNewGame(){
        try {
            int size = topMenu.getBoardSize();
            gameUtils.startNewGame(size, rightMenu.getRedType(), leftMenu.getBlueType(), topMenu.getMode());
            root.setCenter(null);
            root.setCenter(gameBoardUI.createGameBoard(size, gameUtils));
        }
        catch(IllegalArgumentException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Settings");
            alert.setHeaderText("Cannot start new game");
            alert.setContentText(ex.getMessage()); // shows “Board size must be between 5 and 11.”
            alert.showAndWait();
        }
    }
 }

