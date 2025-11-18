package com.sosgame.Logic;

import java.util.Random;

public class ComputerPlayer extends Player {

    private final Random rand = new Random(); // reuse single Random instance

    public ComputerPlayer(String color) {
        super(color, "Computer");
    }

    // Decide where to move
    public int[] chooseMove(GameBoard board) {
        setSelectedLetter(chooseLetter()); // pick S or O each turn
        return findEmptyCell(board);
    }

    // Randomly choose 'S' or 'O'
    private char chooseLetter() {
        return rand.nextBoolean() ? 'S' : 'O';
    }

    // Find a random empty cell
    private int[] findEmptyCell(GameBoard board) {
        int row, col;
        do {
            row = rand.nextInt(board.getSize());
            col = rand.nextInt(board.getSize());
        } while (!board.isCellEmpty(row, col));
        return new int[]{row, col};
    }
}

