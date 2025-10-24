package com.sosgame.Logic;

public class SimpleGame extends Game {

    public SimpleGame(GameBoard board, Player playerRed, Player playerBlue) {
        super(board, "Simple", playerRed.getType(), playerBlue.getType());
    }
}

