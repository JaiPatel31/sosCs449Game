package com.sosgame.Logic;

public class GeneralGame extends Game {
    public GeneralGame(GameBoard board, Player red, Player blue) {
        super(board, "General", red, blue);
    }

    @Override
    public void makeMove(int r, int c) {
        if (gameOver || !board.isCellEmpty(r, c)) return;

        Player current = playerBlue.isTurn() ? playerBlue : playerRed;
        char letter = current.getSelectedLetter();
        board.placeLetter(r, c, letter, current.getColor());

        int scoreGained = countNewSOS(r, c);
        if (scoreGained > 0) {
            current.incrementScoreByNumber(scoreGained);
            // stay on turn
        } else {
            switchTurn();
        }

        if (board.isBoardFull()) {
            finishGame();
        }
    }

    private int countNewSOS(int r, int c) {
        int count = 0;
        char[][] g = board.getletterBoard();
        int[][] dirs = {{1,0},{0,1},{1,1},{1,-1}};
        char ch = g[r][c];
        if (ch == 'O') {
            for (int[] d : dirs)
                if (isSOS(g, r - d[0], c - d[1], r, c, r + d[0], c + d[1])) count++;
        } else if (ch == 'S') {
            for (int[] d : dirs)
                if (isSOS(g, r, c, r + d[0], c + d[1], r + 2*d[0], c + 2*d[1])) count++;
            for (int[] d : dirs)
                if (isSOS(g, r, c, r - d[0], c - d[1], r - 2*d[0], c - 2*d[1])) count++;
        }
        return count;
    }

    private boolean isSOS(char[][] g, int r1,int c1,int r2,int c2,int r3,int c3){
        return r1>=0 && r2>=0 && r3>=0 && c1>=0 && c2>=0 && c3>=0 &&
                r1<g.length && r2<g.length && r3<g.length &&
                c1<g[0].length && c2<g[0].length && c3<g[0].length &&
                g[r1][c1]=='S' && g[r2][c2]=='O' && g[r3][c3]=='S';
    }

    private void finishGame() {
        if (playerBlue.getScore() > playerRed.getScore()) playerBlue.setWinner(true);
        else if (playerRed.getScore() > playerBlue.getScore()) playerRed.setWinner(true);
        gameOver = true;
    }
}
