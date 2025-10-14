package com.sosgame.UI;
import com.sosgame.Logic.GameBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class gameUI {

    public BorderPane createContent(){
        BorderPane root = new BorderPane();

        TopMenu topMenu = new TopMenu();
        root.setTop(topMenu.createTopMenu());

        LeftMenu leftMenu = new LeftMenu();
        root.setLeft(leftMenu.createLeftMenu());

        RightMenu rightMenu = new RightMenu();
        root.setRight(rightMenu.createRightMenu());

        GameBoardUI gameBoardUI = new GameBoardUI();
        root.setCenter(gameBoardUI.createGameBoard(5)); //default size 8x8
        return  root;

    }




}
