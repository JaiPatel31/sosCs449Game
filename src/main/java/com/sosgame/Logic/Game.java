package com.sosgame.Logic;

public abstract class Game {
    protected GameBoard board;
    protected Player playerRed;
    protected Player playerBlue;
    protected String mode;
    protected boolean gameOver = false;

    public Game(GameBoard board, String mode, String redType, String blueType) {
        this.board = board;
        this.mode = mode;
        this.playerRed = new Player("Red", redType);
        this.playerBlue = new Player("Blue", blueType);
        this.playerBlue.setTurn(true);
        this.playerRed.setTurn(false);
    }
}

