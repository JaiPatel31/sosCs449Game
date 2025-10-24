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
    // Initialize game state
    public void initialize() {
        playerRed.resetScore();
        playerBlue.resetScore();
        playerRed.setWinner(false);
        playerBlue.setWinner(false);
        gameOver = false;
    }

    //Getters
    public GameBoard getBoard() {
        return board;
    }

    public Player getPlayerRed() {
        return playerRed;
    }

    public Player getPlayerBlue() {
        return playerBlue;
    }

    public String getMode() {
        return mode;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public abstract boolean checkWin(Player currentPlayer);
}

