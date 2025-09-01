package com.sosgame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.sosgame.gameUI;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App extends Application {


    @Override
    public void start(Stage PrimaryStage){
        gameUI gameUI = new gameUI();
        Scene scene = new Scene(gameUI.createContent(), 600, 600);

        PrimaryStage.setTitle("SOS Game");
        PrimaryStage.setScene(scene);

        PrimaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}