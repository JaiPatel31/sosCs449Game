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
            validSize();
        }
        catch(IllegalArgumentException ex){
            illegalSize(ex);
        }
    }

    private void illegalSize(IllegalArgumentException ex){
        gameUtils.startNewGame(5, rightMenu.getRedType(), leftMenu.getBlueType(), topMenu.getMode());
        root.setCenter(null);
        root.setCenter(gameBoardUI.createGameBoard(5, gameUtils));
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Settings");
        alert.setHeaderText("Started Game with default value 5");
        alert.setContentText(ex.getMessage()); // shows “Board size must be between 5 and 11.”
        alert.showAndWait();
    }

    private void validSize(){
        int size = topMenu.getBoardSize();
        gameUtils.startNewGame(size, rightMenu.getRedType(), leftMenu.getBlueType(), topMenu.getMode());
        root.setCenter(null);
        root.setCenter(gameBoardUI.createGameBoard(size, gameUtils));
    }
 }

