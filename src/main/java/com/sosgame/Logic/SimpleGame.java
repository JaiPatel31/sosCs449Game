package com.sosgame.Logic;

public class SimpleGame extends Game {

    public SimpleGame(GameBoard board, Player red, Player blue) {
        super(board, "Simple", red, blue);
    }

    @Override
    public void makeMove(int row, int col) {
        if (gameOver || !board.isCellEmpty(row, col)) return;

        Player current = getCurrentPlayer();
        char letter = current.getSelectedLetter();

        board.placeLetter(row, col, letter, current.getColor());

        if (checkSOS(row, col)) {
            current.setWinner(true);
            gameOver = true;
        } else {
            switchTurn();
        }
    }

}
