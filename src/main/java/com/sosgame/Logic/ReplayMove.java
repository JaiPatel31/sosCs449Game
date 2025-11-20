package com.sosgame.Logic;

public class ReplayMove {
    public int row, col;
    public char letter;
    public String player;

    public ReplayMove(int row, int col, char letter, String player) {
        this.row = row;
        this.col = col;
        this.letter = letter;
        this.player = player;
    }
}

