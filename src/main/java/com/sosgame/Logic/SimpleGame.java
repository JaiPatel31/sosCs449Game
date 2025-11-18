package com.sosgame.Logic;

public class SimpleGame extends Game {
    // Simple game variant: first SOS wins
    public SimpleGame(GameBoard board, Player red, Player blue, Boolean isRecording) {
        // Call base constructor with mode name
        super(board, "Simple", red, blue,isRecording);
    }

    @Override
    public void makeMove(int r, int c) {
        // Reject moves to occupied cells
        if (!board.isCellEmpty(r, c)) {
            throw new IllegalArgumentException("Cell is occupied.");
        } else if (gameOver) {
            // Reject moves after game over
            throw new IllegalStateException("Game is already over.");
        }

        // Determine current player and chosen letter
        Player current = playerBlue.isTurn() ? playerBlue : playerRed;
        char letter = current.getSelectedLetter();

        // Place the letter on the board
        board.placeLetter(r, c, letter, current.getColor());

        checkWin(current,r,c);
    }

    private void checkWin(Player current, int r, int c) {
        // If this move formed SOS, current player wins immediately
        if (checkSOS(r, c)) {
            current.setWinner(true);
            gameOver = true;
        } else if (board.isBoardFull()) {
            // If board is full without SOS, game ends in draw (no winner set)
            gameOver = true;
        } else {
            // Otherwise switch turn
            switchTurn();
        }
    }
}
