package com.sosgame.Logic;

public class SimpleGame extends Game {

    public SimpleGame(GameBoard board, Player playerRed, Player playerBlue) {
        super(board, "Simple", playerRed.getType(), playerBlue.getType());
    }

    protected boolean checkWin(Player currentPlayer) {
        // Simple game win logic to be implemented
        return false;
    }
}

