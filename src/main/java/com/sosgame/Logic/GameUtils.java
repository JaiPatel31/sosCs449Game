package com.sosgame.Logic;

public class GameUtils {

    private GameBoard gameBoard;
    private Game game;
    private String gameMode;

    public void startNewGame(int boardSize, String gameMode, String playerRedType, String playerBlueType) {
        this.gameBoard = new GameBoard(boardSize);
        this.gameMode = gameMode;

        Player red = new Player("Red", playerRedType);
        Player blue = new Player("Blue", playerBlueType);
        blue.setTurn(true);

        if ("Simple".equalsIgnoreCase(gameMode)) {
            game = new SimpleGame(gameBoard, red, blue);
        } else if ("General".equalsIgnoreCase(gameMode)) {
            game = new GeneralGame(gameBoard, red, blue);
        } else {
            throw new IllegalArgumentException("Invalid game mode: " + gameMode);
        }

        game.initialize();
    }

    // delegate move logic to the Game subclass
    public void makeMove(int row, int col) {
        if (game == null || game.isGameOver()) return;
        game.makeMove(row, col);
    }

    // --- Getters used by UI ---
    public GameBoard getGameBoard() { return gameBoard; }
    public Player getPlayerRed() { return game != null ? game.getPlayerRed() : null; }
    public Player getPlayerBlue() { return game != null ? game.getPlayerBlue() : null; }

    public Player getCurrentPlayer() {
        if (game == null) return null;
        return game.getPlayerRed().isTurn() ? game.getPlayerRed() : game.getPlayerBlue();
    }

    public String getGameMode() { return gameMode; }
    public boolean isGameOver() { return game != null && game.isGameOver(); }

    public String getWinner() {
        if (game == null) return null;
        if (game.getPlayerRed().isWinner()) return game.getPlayerRed().color;
        if (game.getPlayerBlue().isWinner()) return game.getPlayerBlue().color;
        return null;
    }
}
