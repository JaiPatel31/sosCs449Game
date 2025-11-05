package com.sosgame.Logic;

import java.util.List;
import com.sosgame.UI.GameBoardUI;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

public class GameUtils {

    private Game game;
    private GameBoard board;
    private String mode;
    private GameBoardUI gameBoardUI;

    public void startNewGame(int boardSize, String gameMode, String playerRedType, String playerBlueType,GameBoardUI gameBoardUI) {
        this.board = new GameBoard(boardSize);
        this.mode = gameMode;
        this.gameBoardUI = gameBoardUI;

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
        // stop if game over or next player is human
        if (game == null || game.isGameOver() || !(getCurrentPlayer() instanceof ComputerPlayer)) {
            return;
        }

        Player ai = getCurrentPlayer();

        // make one move
        int[] move = ((ComputerPlayer) ai).chooseMove(game.getBoard());
        game.makeMove(move[0], move[1]);

        // update the board immediately
        gameBoardUI.updateBoardDisplay();

        // schedule next computer move in 500 ms (non-blocking)
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(e -> autoStartIfComputerTurn()); // recurse if still AI’s turn
        pause.play();
    }



    // Player move delegation
    public void makeMove(int row, int col) {
        if (game == null || game.isGameOver()) return;

        // human move
        game.makeMove(row, col);
        gameBoardUI.updateBoardDisplay();

        // now let the computer continue if needed
        autoStartIfComputerTurn();
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

