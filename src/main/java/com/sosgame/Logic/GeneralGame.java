package com.sosgame.Logic;

public class GeneralGame extends Game {

    public GeneralGame(GameBoard board, Player playerRed, Player playerBlue) {
        super(board, "General", playerRed.getType(), playerBlue.getType());
    }
}

