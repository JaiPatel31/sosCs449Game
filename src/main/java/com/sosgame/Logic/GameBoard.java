package com.sosgame.Logic;

// Represents the game board for SOS
public class GameBoard {

    private char[][] ownerBoard;    // Tracks which player owns each cell
    private char[][] letterBoard;   // Tracks the letter in each cell
    private int size;               // Size of the board

    // Constructor: creates an empty board of given size
    public GameBoard(int size) {
        if (size < 3 || size > 11) {
            buildBoard(5); // Default to size 5 if invalid
            throw new IllegalArgumentException("Board size must be between 3 and 11.");
        }
        buildBoard(size);
    }

    // Constructor: copies an existing board state
    public GameBoard(GameBoard gameBoard) {
        this.size = gameBoard.size;
        this.letterBoard = new char[size][size];
        this.ownerBoard = new char[size][size];
        // Copy each cell from the given board
        for (int i = 0; i < size; i++) {
            System.arraycopy(gameBoard.letterBoard[i], 0, this.letterBoard[i], 0, size);
            System.arraycopy(gameBoard.ownerBoard[i], 0, this.ownerBoard[i], 0, size);
        }
    }

    // Helper: initializes the board arrays
    private void buildBoard(int size){
        this.size = size;
        this.letterBoard = new char[size][size];
        this.ownerBoard = new char[size][size];
        // Fill all cells with spaces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.letterBoard[i][j] = ' ';
                this.ownerBoard[i][j] = ' ';
            }
        }
    }

    // Returns the owner board
    public char[][] getownerBoard() {
        return ownerBoard;
    }

    // Returns the letter board
    public char[][] getletterBoard() {
        return letterBoard;
    }

    // Returns the board size
    public int getSize() {
        return size;
    }

    // Checks if a cell is empty
    public boolean isCellEmpty(int row, int col) {
        return letterBoard[row][col] == ' ' && ownerBoard[row][col] == ' ';
    }

    //Checks if Board is full
    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (letterBoard[i][j] == ' ' && ownerBoard[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    // Places a letter for a player at the given cell
    public void placeLetter(int row, int col, char letter, String player) {
        if (isCellEmpty(row, col) && (letter == 'S' || letter == 'O') && (player.equals("Red") || player.equals("Blue"))) {
            letterBoard[row][col] = letter;
            ownerBoard[row][col] = player.charAt(0); // 'R' for Red, 'B' for Blue
        } else {
            // Throw error for invalid move
            if (!isCellEmpty(row, col)) {
                throw new IllegalArgumentException("Cell is already occupied.");
            } else if (letter != 'S' && letter != 'O') {
                throw new IllegalArgumentException("Invalid letter. Only 'S' or 'O' are allowed.");
            } else if (!player.equals("Red") && !player.equals("Blue")) {
                throw new IllegalArgumentException("Invalid player. Only 'Red' or 'Blue' are allowed.");
            }
        }
    }
}
