package com.sosgame.Logic;

public class SimpleGame extends Game {
    public SimpleGame(GameBoard board, Player red, Player blue) {
        super(board, "Simple", red, blue);
    }

    @Override
    public void makeMove(int r, int c) {
        if (!board.isCellEmpty(r, c)) {
            throw new IllegalArgumentException("Cell is occupied.");
        } else if (gameOver) {
            throw new IllegalStateException("Game is already over.");
        }

        Player current = playerBlue.isTurn() ? playerBlue : playerRed;
        char letter = current.getSelectedLetter();

        board.placeLetter(r, c, letter, current.getColor());

        if (checkSOS(r, c)) {
            current.setWinner(true);
            gameOver = true;
        } else if (board.isBoardFull()) {
            gameOver = true;
        } else {
            switchTurn();
        }
    }
}
