package com.sosgame.Logic;

public abstract class Game {
    protected GameBoard board;
    protected Player playerRed;
    protected Player playerBlue;
    protected String mode;
    protected boolean gameOver = false;

    public Game(GameBoard board, String mode, Player red, Player blue) {
        this.board = board;
        this.mode = mode;
        this.playerRed = red;
        this.playerBlue = blue;
    }

    public void initialize() {
        playerRed.resetScore();
        playerBlue.resetScore();
        playerRed.setWinner(false);
        playerBlue.setWinner(false);
        gameOver = false;
        playerBlue.setTurn(true);
        playerRed.setTurn(false);
    }

    public abstract void makeMove(int row, int col);

    // Shared SOS detection
    protected boolean checkSOS(int row, int col) {
        char[][] grid = board.getletterBoard();
        char placed = grid[row][col];
        int[][] directions = {
                {0, 1}, {1, 0}, {1, 1}, {1, -1}
        };

        if (placed == 'S') {
            return checkSOSFromS(row, col, grid, directions);
        } else if (placed == 'O') {
            return checkSOSFromO(row, col, grid, directions);
        }
        return false;
    }

    private boolean checkSOSFromS(int row, int col, char[][] grid, int[][] directions) {
        for (int[] dir : directions) {
            int r1 = row + dir[0], c1 = col + dir[1];
            int r2 = row + 2 * dir[0], c2 = col + 2 * dir[1];
            if (isInBounds(r1, c1, grid) && isInBounds(r2, c2, grid)) {
                if (grid[r1][c1] == 'O' && grid[r2][c2] == 'S') return true;
            }
            r1 = row - dir[0];
            c1 = col - dir[1];
            r2 = row - 2 * dir[0];
            c2 = col - 2 * dir[1];
            if (isInBounds(r1, c1, grid) && isInBounds(r2, c2, grid)) {
                if (grid[r1][c1] == 'O' && grid[r2][c2] == 'S') return true;
            }
        }
        return false;
    }

    private boolean checkSOSFromO(int row, int col, char[][] grid, int[][] directions) {
        for (int[] dir : directions) {
            int r1 = row - dir[0], c1 = col - dir[1];
            int r2 = row + dir[0], c2 = col + dir[1];
            if (isInBounds(r1, c1, grid) && isInBounds(r2, c2, grid)) {
                if (grid[r1][c1] == 'S' && grid[r2][c2] == 'S') return true;
            }
        }
        return false;
    }

    private boolean isInBounds(int row, int col, char[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    // Utility methods
    protected Player getCurrentPlayer() {
        return playerRed.isTurn() ? playerRed : playerBlue;
    }

    protected void switchTurn() {
        if (playerRed.isTurn()) {
            playerRed.setTurn(false);
            playerBlue.setTurn(true);
        } else {
            playerRed.setTurn(true);
            playerBlue.setTurn(false);
        }
    }

    // Getters
    public GameBoard getBoard() {
        return board;
    }

    public Player getPlayerRed() {
        return playerRed;
    }

    public Player getPlayerBlue() {
        return playerBlue;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}