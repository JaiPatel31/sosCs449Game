package com.sosgame.Logic;

public class GameUtils {
    private GameBoard gameBoard; // The game board instance
    private Player PlayerRed;    // Red player
    private Player PlayerBlue;   // Blue player
    private String gameMode;     // "Simple" or "General" game mode

    // Start a new game with given parameters
    public void startNewGame(int boardSize, String gameMode, String player1Type, String player2Type) {
        this.gameBoard = new GameBoard(boardSize); // Create new board
        this.gameMode = gameMode;                  // Set game mode
        this.PlayerRed = new Player("Red", player1Type);   // Create Red player
        this.PlayerBlue = new Player("Blue", player2Type); // Create Blue player
        this.PlayerBlue.setTurn(true);//Blue starts first
        this.PlayerRed.setTurn(false);

    }

    // Copy the board state
    void gameBoard(GameBoard gameBoard) {
        this.gameBoard = new GameBoard(gameBoard);
    }

    // Make a move for the current player
    public void makeMove(int row, int col) {
        Player currentPlayer = PlayerRed.isTurn() ? PlayerRed : PlayerBlue; // Get current player
        gameBoard.placeLetter(row, col, currentPlayer.getSelectedLetter(), currentPlayer.getColor()); // Place letter
        switchTurn(); // Switch turns after move
    }

    // Switch turns between players
    void switchTurn() {
        if (PlayerRed.isTurn()) {
            PlayerRed.setTurn(false);
            PlayerBlue.setTurn(true);
        } else {
            PlayerRed.setTurn(true);
            PlayerBlue.setTurn(false);
        }
    }

    // GETTERS
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

    // Check if the game is over based on players' winner status
    public boolean isGameOver () {
        if (PlayerRed.isTurn()) {
            return PlayerRed.isWinner() || PlayerBlue.isWinner();
        } else {
            return PlayerBlue.isWinner() || PlayerRed.isWinner();
        }
    }

    // Return the winning player
    public Player getWinner() {
        if (PlayerRed.isWinner()) {
            return PlayerRed;
        } else if (PlayerBlue.isWinner()) {
            return PlayerBlue;
        } else {
            return null; // No winner yet
        }
    }

    // Get the current player
    public Player getCurrentPlayer() {
        return PlayerRed.isTurn() ? PlayerRed : PlayerBlue;
    }
}
