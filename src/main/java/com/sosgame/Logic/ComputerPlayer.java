package com.sosgame.Logic;

import java.util.Random;

public class ComputerPlayer extends Player{
    // Constructor to create a new computer player
    public ComputerPlayer(String color) {
        super(color, "Computer");
    }


    public int[] chooseMove(GameBoard board) {
        Random rand = new Random();
        int row, col;

        do {
            row = rand.nextInt(board.getSize());
            col = rand.nextInt(board.getSize());
        } while (!board.isCellEmpty(row, col));

        return new int[]{row, col};
    }
}
