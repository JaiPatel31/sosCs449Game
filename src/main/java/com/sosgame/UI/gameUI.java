package com.sosgame.UI;
import javafx.scene.layout.*;
import com.sosgame.Logic.GameUtils;

public class gameUI {

    public BorderPane createGUI(){
        GameUtils gameUtils = new GameUtils();
        BorderPane root = new BorderPane();

        TopMenu topMenu = new TopMenu();
        root.setTop(topMenu.createTopMenu(gameUtils));

        LeftMenu leftMenu = new LeftMenu();
        root.setLeft(leftMenu.createLeftMenu(gameUtils));

        RightMenu rightMenu = new RightMenu();
        root.setRight(rightMenu.createRightMenu(gameUtils));

        GameBoardUI gameBoardUI = new GameBoardUI();
        root.setCenter(gameBoardUI.createGameBoard(5, gameUtils)); //default size 8x8
        return  root;

    }




}
