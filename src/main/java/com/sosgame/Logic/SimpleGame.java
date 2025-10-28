package com.sosgame.Logic;

public class SimpleGame extends Game {

    public SimpleGame(GameBoard board, Player playerRed, Player playerBlue) {
        super(board, "Simple", playerRed.getType(), playerBlue.getType());
    }

    protected boolean checkWin(Player currentPlayer) {
        if (this.checkWin(currentPlayer)) {
            currentPlayer.setWinner(true);
            this.gameOver = true;
            return true;
        } else {
            return false;
        }
    }


}

