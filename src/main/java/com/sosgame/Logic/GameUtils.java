package com.sosgame.Logic;

public class GameUtils {
    private GameBoard gameBoard;
    private Player PlayerRed;
    private Player PlayerBlue;
    private String gameMode; // "Simple" or "General"

    // Start a new game with given parameters
    public void startNewGame(int boardSize, String gameMode, String player1Type, String player2Type) {
        this.gameBoard = new GameBoard(boardSize);
        this.gameMode = gameMode;
        this.PlayerRed = new Player("Red", player1Type);
        this.PlayerBlue = new Player("Blue", player2Type);
        // Red always starts first
        this.PlayerRed.setTurn(true);
        this.PlayerBlue.setTurn(false);
    }

    void gameBoard(GameBoard gameBoard) {
        this.gameBoard = new GameBoard(gameBoard);
    }

    void MakeMove(int row, int col) {
        Player currentPlayer = PlayerRed.isTurn() ? PlayerRed : PlayerBlue;
        gameBoard.placeLetter(row, col, currentPlayer.getSelectedLetter(), currentPlayer.getColor());
        // After placing the letter, switch turns
        SwitchTurn();
    }

    void SwitchTurn() {
        if (PlayerRed.isTurn()) {
            PlayerRed.setTurn(false);
            PlayerBlue.setTurn(true);
        } else {
            PlayerRed.setTurn(true);
            PlayerBlue.setTurn(false);
        }
    }

    //GETTERS
    public GameBoard getGameBoard() {
        return gameBoard;
    }
    public Player getPlayerRed() {
        return PlayerRed;
    }
    public Player getPlayerBlue() {
        return  PlayerBlue;
    }
    public String getGameMode() {
        return gameMode;
    }

}
