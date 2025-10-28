package com.sosgame.Logic;

public class GeneralGame extends Game {

    public GeneralGame(GameBoard board, Player red, Player blue) {
        super(board, "General", red, blue);
    }

    @Override
    public void makeMove(int row, int col) {
        if (gameOver || !board.isCellEmpty(row, col)) return;

        Player current = getCurrentPlayer();
        char letter = current.getSelectedLetter();

        board.placeLetter(row, col, letter, current.getColor());

        int sosCount = countSOS(row, col);

        if (sosCount > 0) {
            current.incrementScoreByNumber(sosCount);
            // same player goes again
        } else {
            switchTurn();
        }

        if (board.isBoardFull()) {
            endGame();
        }
    }

    private int countSOS(int row, int col) {
        char[][] grid = board.getletterBoard();
        int[][] dirs = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
        int n = grid.length;
        int count = 0;
        char letter = grid[row][col];

        if (letter == 'O') {
            for (int[] d : dirs)
                if (isSOS(grid, n, row - d[0], col - d[1], row, col, row + d[0], col + d[1])) count++;
        } else if (letter == 'S') {
            for (int[] d : dirs)
                if (isSOS(grid, n, row, col, row + d[0], col + d[1], row + 2 * d[0], col + 2 * d[1])) count++;
            for (int[] d : dirs)
                if (isSOS(grid, n, row, col, row - d[0], col - d[1], row - 2 * d[0], col - 2 * d[1])) count++;
        }

        return count;
    }

    private boolean isSOS(char[][] grid, int n, int r1, int c1, int r2, int c2, int r3, int c3) {
        if (r1 < 0 || r1 >= n || c1 < 0 || c1 >= n || r3 < 0 || r3 >= n || c3 < 0 || c3 >= n)
            return false;
        return grid[r1][c1] == 'S' && grid[r2][c2] == 'O' && grid[r3][c3] == 'S';
    }

    private void endGame() {
        if (playerBlue.getScore() > playerRed.getScore()) playerBlue.setWinner(true);
        else if (playerRed.getScore() > playerBlue.getScore()) playerRed.setWinner(true);
        gameOver = true;
    }

}
