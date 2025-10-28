package com.sosgame.Logic;

public class GameUtils {

    private Game game;
    private GameBoard board;
    private String mode;

    // Create a new game
    public void startNewGame(int boardSize, String gameMode, String playerRedType, String playerBlueType) {
        this.board = new GameBoard(boardSize);
        this.mode = gameMode;

        Player red = new Player("Red", playerRedType);
        Player blue = new Player("Blue", playerBlueType);
        blue.setTurn(true);

        if ("Simple".equalsIgnoreCase(gameMode)) {
            game = new SimpleGame(board, red, blue);
        } else {
            game = new GeneralGame(board, red, blue);
        }

        game.initialize();
    }

    // Player move delegation
    public void makeMove(int row, int col) {
        if (game == null || game.isGameOver()) return;
        game.makeMove(row, col);
    }

    //Accessors
    public GameBoard getGameBoard() { return board; }
    public Player getPlayerRed() { return game != null ? game.getPlayerRed() : null; }
    public Player getPlayerBlue() { return game != null ? game.getPlayerBlue() : null; }
    public Player getCurrentPlayer() {
        return game != null && game.getPlayerRed().isTurn()
                ? game.getPlayerRed() : game.getPlayerBlue();
    }

    public boolean isGameOver() { return game != null && game.isGameOver(); }
    public Player getWinner() {
        if (game == null) return null;
        if (game.getPlayerRed().isWinner()) return game.getPlayerRed();
        if (game.getPlayerBlue().isWinner()) return game.getPlayerBlue();
        return null;
    }

    public String getGameMode() { return mode; }
}

