package com.sosgame.Logic;

public class SimpleGame extends Game {
    public SimpleGame(GameBoard board, Player red, Player blue) {
        super(board, "Simple", red, blue);
    }

    @Override
    public void makeMove(int r, int c) {
        if (gameOver || !board.isCellEmpty(r, c)) return;

        Player current = playerBlue.isTurn() ? playerBlue : playerRed;
        char letter = current.getSelectedLetter();

        board.placeLetter(r, c, letter, current.getColor());

        if (checkSOS(r, c)) {
            current.setWinner(true);
            gameOver = true;
        } else {
            switchTurn();
        }
    }
}
