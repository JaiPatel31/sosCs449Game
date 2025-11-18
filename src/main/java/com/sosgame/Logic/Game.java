package com.sosgame.Logic;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    // The game board shared by subclasses
    protected GameBoard board;
    // Two players in the game
    protected Player playerRed;
    protected Player playerBlue;
    // Game mode string (e.g. "Simple" or "General")
    protected String mode;
    // Whether the game is finished
    protected boolean gameOver;
    // Whether the game is being recorded (for replay)
    protected boolean isRecording = false;
    // Recorder instance for saving moves if recording is enabled
    private GameRecorder recorder;
    // Track the SOS lines formed during play for UI highlighting
    protected List<SOSLine> completedSOSLines = new ArrayList<>();
    public static class SOSLine {
        public final int startRow, startCol, endRow, endCol;
        public final String color;

        // Simple container for an SOS line's endpoints and the player's color
        public SOSLine(int sr, int sc, int er, int ec, String color) {
            this.startRow = sr;
            this.startCol = sc;
            this.endRow = er;
            this.endCol = ec;
            this.color = color;
        }
    }

    // Return the recorded SOS lines
    public List<SOSLine> getCompletedSOSLines() {
        return completedSOSLines;
    }

    // Clear recorded SOS lines (used internally when starting a new game)
        public void resetSOSLines() {
        completedSOSLines.clear();
    }

    // Construct a game with a board, mode, and two players
    public Game(GameBoard board, String mode, Player red, Player blue) {
        this.board = board;
        this.mode = mode;
        this.playerRed = red;
        this.playerBlue = blue;
        this.gameOver = false;
    }

    // Overloaded constructor to include recording option
    public Game(GameBoard board, String mode, Player red, Player blue, boolean isRecording) {
        this.board = board;
        this.mode = mode;
        this.playerRed = red;
        this.playerBlue = blue;
        this.gameOver = false;
        this.isRecording = isRecording;
    }
    // Initialize or reset runtime state before starting a game
    public void initialize() {
        playerRed.resetScore();
        playerBlue.resetScore();
        playerRed.setWinner(false);
        playerBlue.setWinner(false);
        gameOver = false;
        // Clear any recorded SOS lines when starting/resetting a game
        resetSOSLines();
        playerRed.setTurn(false);
        playerBlue.setTurn(true); // blue starts

        if(isRecording) {
                recorder = new GameRecorder();
                recorder.start(mode, board.getSize());
        }
    }

    // Subclasses implement how a move is applied (Simple vs General rules)
    public abstract void makeMove(int row, int col);

    // Shared SOS pattern check invoked after placing a letter at (r,c)
    protected boolean checkSOS(int r, int c) {
        char[][] grid = board.getletterBoard();
        char ch = grid[r][c];
        // Directions to check: down, right, diag down-right, diag down-left
        int[][] dirs = {{1,0},{0,1},{1,1},{1,-1}};
        // Determine which player made the move (the one whose turn it is)
        Player currentPlayer = playerBlue.isTurn() ? playerBlue : playerRed;

        if (ch == 'O') {
            return checkSOSWithO(grid, r, c, dirs, currentPlayer);
        } else if (ch == 'S') {
            return checkSOSWithS(grid, r, c, dirs, currentPlayer);
        }
        return false;
    }

    // Check for SOS patterns where the placed letter is 'O' (O must be center)
    private boolean checkSOSWithO(char[][] grid, int r, int c, int[][] dirs, Player currentPlayer) {
        boolean found = false;
        for (int[] d : dirs) {
            // For each direction, check one cell before and one after
            if (isSOS(grid, r - d[0], c - d[1], r, c, r + d[0], c + d[1])) {
                completedSOSLines.add(
                        new SOSLine(r - d[0], c - d[1], r + d[0], c + d[1], currentPlayer.getColor())
                );
                found = true;
            }
        }
        return found;
    }

    // Check for SOS patterns where the placed letter is 'S' (S can be start or end)
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

    // Helper to verify three cells form 'S','O','S'
    private boolean isSOS(char[][] g, int r1,int c1,int r2,int c2,int r3,int c3) {
        return in(g,r1,c1) && in(g,r2,c2) && in(g,r3,c3)
                && g[r1][c1]=='S' && g[r2][c2]=='O' && g[r3][c3]=='S';
    }
    // Check bounds for a given cell
    private boolean in(char[][] g,int r,int c){
        return r>=0 && c>=0 && r<g.length && c<g[0].length;
    }

    // Toggle turns between players
    protected void switchTurn() {
        playerRed.setTurn(!playerRed.isTurn());
        playerBlue.setTurn(!playerBlue.isTurn());
    }

    //Recording the move function
    public void recordMove(int r, int c, char letter, Player player) {
        if (!isRecording || recorder == null) return;

        recorder.recordMove(r, c, letter, player.getColor());
    }
    //stop recording function
    public void stopRecordingIfActive() {
        if (isRecording && recorder != null) {
            recorder.stop();
            isRecording = false;
        }
    }
    // Getters
    public GameBoard getBoard(){return board;}
    public Player getPlayerRed(){return playerRed;}
    public Player getPlayerBlue(){return playerBlue;}
    public boolean isGameOver(){return gameOver;}
}
