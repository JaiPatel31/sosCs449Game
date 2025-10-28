package com.sosgame.Logic;

public abstract class Game {
    protected GameBoard board;
    protected Player playerRed;
    protected Player playerBlue;
    protected String mode;
    protected boolean gameOver = false;

    public Game(GameBoard board, String mode, String redType, String blueType) {
        this.board = board;
        this.mode = mode;
        this.playerRed = new Player("Red", redType);
        this.playerBlue = new Player("Blue", blueType);
        this.playerBlue.setTurn(true);
        this.playerRed.setTurn(false);
    }
    // Initialize game state
    public void initialize() {
        playerRed.resetScore();
        playerBlue.resetScore();
        playerRed.setWinner(false);
        playerBlue.setWinner(false);
        gameOver = false;
    }

    //Getters
    public GameBoard getBoard() {
        return board;
    }

    public Player getPlayerRed() {
        return playerRed;
    }

    public Player getPlayerBlue() {
        return playerBlue;
    }

    public String getMode() {
        return mode;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    protected abstract boolean checkWin(Player currentPlayer);

    private boolean checkSOS(int row, int col) {
        char[][] grid = board.getletterBoard();
        char placed = grid[row][col];
        int[][] directions = {
                {0, 1},   // horizontal
                {1, 0},   // vertical
                {1, 1},   // diagonal down-right
                {1, -1}   // diagonal down-left
        };

        if (placed == 'S') {
            // Check both directions for S-O-S
            for (int[] dir : directions) {
                int r1 = row + dir[0], c1 = col + dir[1];
                int r2 = row + 2 * dir[0], c2 = col + 2 * dir[1];
                if (isInBounds(r1, c1, grid) && isInBounds(r2, c2, grid)) {
                    if (grid[r1][c1] == 'O' && grid[r2][c2] == 'S') {
                        return true;
                    }
                }
                r1 = row - dir[0]; c1 = col - dir[1];
                r2 = row - 2 * dir[0]; c2 = col - 2 * dir[1];
                if (isInBounds(r1, c1, grid) && isInBounds(r2, c2, grid)) {
                    if (grid[r1][c1] == 'O' && grid[r2][c2] == 'S') {
                        return true;
                    }
                }
            }
        } else if (placed == 'O') {
            // Check one direction each way for S-O-S
            for (int[] dir : directions) {
                int r1 = row - dir[0], c1 = col - dir[1];
                int r2 = row + dir[0], c2 = col + dir[1];
                if (isInBounds(r1, c1, grid) && isInBounds(r2, c2, grid)) {
                    if (grid[r1][c1] == 'S' && grid[r2][c2] == 'S') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isInBounds(int row, int col, char[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

}

