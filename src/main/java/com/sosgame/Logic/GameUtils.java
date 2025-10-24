package com.sosgame.Logic;

import javafx.application.Platform;

public class GameUtils {

    private GameBoard gameBoard;
    private Game game; // The current game instance (Simple or General)
    private String gameMode;

    // Start a new game with given parameters
    public void startNewGame(int boardSize, String gameMode, String playerRedType, String playerBlueType) {
        // Create board
        this.gameBoard = new GameBoard(boardSize);
        this.gameMode = gameMode;

        // Create correct game type (inject board + players)
        if ("Simple".equalsIgnoreCase(gameMode)) {
            game = new SimpleGame(gameBoard, makePlayer("Red",playerRedType), makePlayer("Blue",playerBlueType));
        } else if ("General".equalsIgnoreCase(gameMode)) {
            game = new GeneralGame(gameBoard, makePlayer("Red",playerRedType), makePlayer("Blue",playerBlueType));
        } else {
            throw new IllegalArgumentException("Invalid game mode: " + gameMode);
        }

        game.initialize(); // Reset scores, turns, and winner flags
    }

    // Handle player making a move
    public void makeMove(int row, int col) {
        if (game == null || game.isGameOver()) return;

        Player currentPlayer = getCurrentPlayer();
        char letter = currentPlayer.getSelectedLetter();

        // Place letter on board
        game.getBoard().placeLetter(row, col, letter, currentPlayer.getColor());

        // Check if this move causes a win or score update
        boolean finished = game.checkWin(currentPlayer);

        // Only switch turns if the game continues
        if (!finished && !game.isGameOver()) {
            switchTurn();
        }
    }

    // Switch turns between players
    private void switchTurn() {
        Player red = game.getPlayerRed();
        Player blue = game.getPlayerBlue();

        if (red.isTurn()) {
            red.setTurn(false);
            blue.setTurn(true);
        } else {
            red.setTurn(true);
            blue.setTurn(false);
        }
    }
    //Make Players
    private Player makePlayer(String color, String type) {
        return new Player(color, type);
    }
    // --- Getters used by UI ---

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getPlayerRed() {
        return game != null ? game.getPlayerRed() : null;
    }

    public Player getPlayerBlue() {
        return game != null ? game.getPlayerBlue() : null;
    }

    public Player getCurrentPlayer() {
        if (game == null) return null;
        return game.getPlayerRed().isTurn() ? game.getPlayerRed() : game.getPlayerBlue();
    }

    public String getGameMode() {
        return gameMode;
    }

    public boolean isGameOver() {
        return game != null && game.isGameOver();
    }

    public Player getWinner() {
        if (game == null) return null;
        if (game.getPlayerRed().isWinner()) return game.getPlayerRed();
        if (game.getPlayerBlue().isWinner()) return game.getPlayerBlue();
        return null;
    }
}

