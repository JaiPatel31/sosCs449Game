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

        //Use shared checkSOS() to record lines + count score
        int scoreGained = countAndRecordSOS(r, c, current);

        if (scoreGained > 0) {
            current.incrementScoreByNumber(scoreGained);
            //stays on turn
        } else {
            switchTurn();
        }

        if (board.isBoardFull()) {
            finishGame();
        }
    }

    // Counts new SOS lines formed and records them
    private int countAndRecordSOS(int r, int c, Player current) {
        int countBefore = completedSOSLines.size();

        checkSOS(r, c);

        int countAfter = completedSOSLines.size();
        return countAfter - countBefore;
    }

    private void finishGame() {
        if (playerBlue.getScore() > playerRed.getScore()) playerBlue.setWinner(true);
        else if (playerRed.getScore() > playerBlue.getScore()) playerRed.setWinner(true);
        gameOver = true;
    }
}

