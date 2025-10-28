package com.sosgame.Logic;

public class GeneralGame extends Game {

    public GeneralGame(GameBoard board, Player playerRed, Player playerBlue) {
        super(board, "General", playerRed.getType(), playerBlue.getType());
    }

    protected boolean checkWin(Player currentPlayer) {
        // General game win logic to be implemented
        return false;
    }


}

