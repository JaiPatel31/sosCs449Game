package com.sosgame.Logic;

public class GameBoard {

    private char[][] board;
    private int size;

    // Initialize an empty board with given size
    public GameBoard(int size) {
        this.size = size;
        board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Initialize the board with a predefined state
    public GameBoard(char[][] board) {
        this.size = board.length;
        this.board = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, size);
        }
    }

    // Get the current state of the board
    public char[][] getBoard() {
        return  board;
    }

    // Get the size of the board
    public int getSize() {
        return size;
    }

    // Check if a cell is empty
    public boolean isCellEmpty(int row, int col) {
        return board[row][col] == ' ';
    }
    // Place a letter on the board
    public void placeLetter(int row, int col, char letter) {
        if (isCellEmpty(row, col) && (letter == 'S' || letter == 'O')) {
            board[row][col] = letter;
        }
    }
}
