package com.sosgame.Logic;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    protected GameBoard board;
    protected Player playerRed;
    protected Player playerBlue;
    protected String mode;
    protected boolean gameOver;
    protected List<SOSLine> completedSOSLines = new ArrayList<>();
    //Track the SOS lines formed during the game for UI
    public static class SOSLine {
        public final int startRow, startCol, endRow, endCol;
        public final String color;

        public SOSLine(int sr, int sc, int er, int ec, String color) {
            this.startRow = sr;
            this.startCol = sc;
            this.endRow = er;
            this.endCol = ec;
            this.color = color;
        }
    }

    public List<SOSLine> getCompletedSOSLines() {
        return completedSOSLines;
    }

    public void resetSOSLines() {
        completedSOSLines.clear();
    }

    public Game(GameBoard board, String mode, Player red, Player blue) {
        this.board = board;
        this.mode = mode;
        this.playerRed = red;
        this.playerBlue = blue;
        this.gameOver = false;
    }

    public void initialize() {
        playerRed.resetScore();
        playerBlue.resetScore();
        playerRed.setWinner(false);
        playerBlue.setWinner(false);
        gameOver = false;
        playerRed.setTurn(false);
        playerBlue.setTurn(true); // blue starts
    }

    public abstract void makeMove(int row, int col);

    // Shared SOS pattern check
    protected boolean checkSOS(int r, int c) {
        char[][] grid = board.getletterBoard();
        char ch = grid[r][c];
        int[][] dirs = {{1,0},{0,1},{1,1},{1,-1}};
        Player currentPlayer = playerBlue.isTurn() ? playerBlue : playerRed;

        if (ch == 'O') {
            return checkSOSWithO(grid, r, c, dirs, currentPlayer);
        } else if (ch == 'S') {
            return checkSOSWithS(grid, r, c, dirs, currentPlayer);
        }
        return false;
    }

    private boolean checkSOSWithO(char[][] grid, int r, int c, int[][] dirs, Player currentPlayer) {
        boolean found = false;
        for (int[] d : dirs) {
            if (isSOS(grid, r - d[0], c - d[1], r, c, r + d[0], c + d[1])) {
                completedSOSLines.add(
                        new SOSLine(r - d[0], c - d[1], r + d[0], c + d[1], currentPlayer.getColor())
                );
                found = true;
            }
        }
        return found;
    }

    private boolean checkSOSWithS(char[][] grid, int r, int c, int[][] dirs, Player currentPlayer) {
        boolean found = false;

        for (int[] d : dirs) {
            int dr = d[0], dc = d[1];

            // Case 1: S at start -> (S at r,c) (O at r+dr,c+dc) (S at r+2dr,c+2dc)
            if (isSOS(grid, r, c, r + dr, c + dc, r + 2 * dr, c + 2 * dc)) {
                completedSOSLines.add(
                        new SOSLine(r, c, r + 2 * dr, c + 2 * dc, currentPlayer.getColor())
                );
                found = true;
            }

            // Case 2: S at end -> (S at r-2dr,c-2dc) (O at r-dr,c-dc) (S at r,c)
            if (isSOS(grid, r - 2 * dr, c - 2 * dc, r - dr, c - dc, r, c)) {
                completedSOSLines.add(
                        new SOSLine(r - 2 * dr, c - 2 * dc, r, c, currentPlayer.getColor())
                );
                found = true;
            }
        }

        return found;
    }

    private boolean isSOS(char[][] g, int r1,int c1,int r2,int c2,int r3,int c3) {
        return in(g,r1,c1) && in(g,r2,c2) && in(g,r3,c3)
                && g[r1][c1]=='S' && g[r2][c2]=='O' && g[r3][c3]=='S';
    }
    private boolean in(char[][] g,int r,int c){
        return r>=0 && c>=0 && r<g.length && c<g[0].length;
    }

    protected void switchTurn() {
        playerRed.setTurn(!playerRed.isTurn());
        playerBlue.setTurn(!playerBlue.isTurn());
    }

    // Getters
    public GameBoard getBoard(){return board;}
    public Player getPlayerRed(){return playerRed;}
    public Player getPlayerBlue(){return playerBlue;}
    public boolean isGameOver(){return gameOver;}
}
