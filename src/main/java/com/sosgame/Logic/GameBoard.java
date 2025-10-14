package com.sosgame.Logic;


public class GameBoard {

    private char[][] ownerBoard;
    private char[][] letterBoard;
    private int size;

    // Initialize an empty board with given size
    public GameBoard(int size) {
        if (size < 5 || size > 11) {
            throw new IllegalArgumentException("Board size must be between 5 and 11.");
        }
        this.size = size;
        this.letterBoard = new char[size][size];
        this.ownerBoard = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.letterBoard[i][j] = ' ';
                this.ownerBoard[i][j] = ' ';
            }
        }
    }

    // Initialize the board with a predefined state
    public GameBoard(GameBoard gameBoard) {
        this.size = gameBoard.size;
        this.letterBoard = new char[size][size];
        this.ownerBoard = new char[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(gameBoard.letterBoard[i], 0, this.letterBoard[i], 0, size);
            System.arraycopy(gameBoard.ownerBoard[i], 0, this.ownerBoard[i], 0, size);
        }
    }

    // Get the current state of the owner board
    public char[][] getownerBoard() {
        return  ownerBoard;
    }
    // Get the current state of the letter board
    public char[][] getletterBoard() {
        return letterBoard;
    }

    // Get the size of the board
    public int getSize() {
        return size;
    }

    // Check if a cell is empty
    public boolean isCellEmpty(int row, int col) {

        return letterBoard[row][col] == ' ' && ownerBoard[row][col] == ' ';
    }

    // Place a letter on the board
    public void placeLetter(int row, int col, char letter, String player) {
        if (isCellEmpty(row, col) && (letter == 'S' || letter == 'O') && (player.equals("Red") || player.equals("Blue"))) {
            letterBoard[row][col] = letter;
            ownerBoard[row][col] = player.charAt(0); // 'R' for Red, 'B' for Blue
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
