package com.sosgame.Logic;


public class GameBoard {

    private String[][] board;
    private int size;

    // Initialize an empty board with given size
    public GameBoard(int size) {
        if (size < 5 || size > 11) {
            throw new IllegalArgumentException("Board size must be between 3 and 10.");
        }
        this.size = size;
        board = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = " ";
            }
        }
    }

    // Initialize the board with a predefined state
    public GameBoard(String[][] board) {
        this.size = board.length;
        this.board = new String[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, size);
        }
    }

    // Get the current state of the board
    public String[][] getBoard() {
        return  board;
    }

    // Get the size of the board
    public int getSize() {
        return size;
    }

    // Check if a cell is empty
    public boolean isCellEmpty(int row, int col) {

        return board[row][col] == " ";
    }

    // Place a letter on the board
    public void placeLetter(int row, int col, char letter, String player) {
        if (isCellEmpty(row, col) && (letter == 'S' || letter == 'O') && (player.equals("Red") || player.equals("Blue"))) {
            board[row][col] = String.valueOf(letter) + player.charAt(0); ; // e.g., "SR" for Red's S
        }else {
            if(!isCellEmpty(row, col)){
                throw new IllegalArgumentException("Cell is already occupied.");
            } else if (letter != 'S' && letter != 'O') {
                throw new IllegalArgumentException("Invalid letter. Only 'S' or 'O' are allowed.");
            } else if (!player.equals("Red") && !player.equals("Blue")) {
                throw new IllegalArgumentException("Invalid player. Only 'Red' or 'Blue' are allowed.");
            }
        }
    }
}
