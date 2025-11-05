package com.sosgame.Logic;

import java.util.List;

public class GameUtils {

    private Game game;
    private GameBoard board;
    private String mode;

    public void startNewGame(int boardSize, String gameMode, String playerRedType, String playerBlueType) {
        this.board = new GameBoard(boardSize);
        this.mode = gameMode;

        Player red = createPlayer("Red", playerRedType);
        Player blue = createPlayer("Blue", playerBlueType);

        initializeTurns(red, blue);
        this.game = createGame(gameMode, board, red, blue);
        game.initialize();

        autoStartIfComputerTurn();
    }

    // Set starting turn
    private void initializeTurns(Player red, Player blue) {
        blue.setTurn(true);
        red.setTurn(false);
    }

    // Create player (Human or Computer)
    private Player createPlayer(String color, String type) {
        if ("Computer".equalsIgnoreCase(type)) {
            return new ComputerPlayer(color);
        }
        return new HumanPlayer(color);
    }

    // Create game based on mode
    private Game createGame(String mode, GameBoard board, Player red, Player blue) {
        if ("Simple".equalsIgnoreCase(mode)) {
            return new SimpleGame(board, red, blue);
        }
        return new GeneralGame(board, red, blue);
    }

    private void autoStartIfComputerTurn() {
        System.out.print("Auto-starting computer turn if applicable...\n");
        while (!game.isGameOver() && getCurrentPlayer() instanceof ComputerPlayer) {
            Player ai = getCurrentPlayer();
            int[] move = ((ComputerPlayer) ai).chooseMove(game.getBoard());
            game.makeMove(move[0], move[1]);
        }
    }

    // Player move delegation
    public void makeMove(int row, int col) {
        if (game == null || game.isGameOver()) return;

        // Execute the human move
        game.makeMove(row, col);

        // If game ended, stop
        if (game.isGameOver()) return;

        // Check if next player is a computer
        Player current = getCurrentPlayer();
        if (current.type.equals("Computer")) {
            int[] move = ((ComputerPlayer) current).chooseMove(game.getBoard());
            game.makeMove(move[0], move[1]);
        }
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

    public List<Game.SOSLine> getCompletedSOSLines() {
        return game.getCompletedSOSLines(); // assumes currentGame = Game instance
    }

}

