package com.sosgame.Logic;

public abstract class Game {
    protected GameBoard board;
    protected Player playerRed;
    protected Player playerBlue;
    protected String mode;
    protected boolean gameOver;

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
        if (ch == 'O') {
            for (int[] d : dirs)
                if (isSOS(grid, r - d[0], c - d[1], r, c, r + d[0], c + d[1])) return true;
        } else if (ch == 'S') {
            for (int[] d : dirs)
                if (isSOS(grid, r, c, r + d[0], c + d[1], r + 2*d[0], c + 2*d[1])) return true;
            for (int[] d : dirs)
                if (isSOS(grid, r, c, r - d[0], c - d[1], r - 2*d[0], c - 2*d[1])) return true;
        }
        return false;
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
