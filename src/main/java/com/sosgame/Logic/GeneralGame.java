package com.sosgame.Logic;

public class GeneralGame extends Game {

    // General game rules implementation
    public GeneralGame(GameBoard board, Player red, Player blue, boolean isRecording) {
        // Call base constructor with mode name
        super(board, "General", red, blue,isRecording);
    }

    @Override
    public void makeMove(int r, int c) {
        // Ignore invalid moves or if game already over
        if (gameOver || !board.isCellEmpty(r, c)) return;

        // Determine current player and place their selected letter
        Player current = playerBlue.isTurn() ? playerBlue : playerRed;
        char letter = current.getSelectedLetter();
        board.placeLetter(r, c, letter, current.getColor());

        // Use shared checkSOS() to record lines and compute score gained
        int scoreGained = countAndRecordSOS(r, c, current);

        if (scoreGained > 0) {
            // If score gained, add points and player keeps the turn
            current.incrementScoreByNumber(scoreGained);
        } else {
            // Otherwise switch turn to the other player
            switchTurn();
        }

        // If board full, determine and record the winner
        if (board.isBoardFull()) {
            finishGame();
        }
    }

    // Counts new SOS lines formed by the last move and returns the number
    private int countAndRecordSOS(int r, int c, Player current) {
        int countBefore = completedSOSLines.size();

        checkSOS(r, c);

        int countAfter = completedSOSLines.size();
        return countAfter - countBefore;
    }

    // Determine final winner based on score and mark game over
    private void finishGame() {
        if (playerBlue.getScore() > playerRed.getScore()) playerBlue.setWinner(true);
        else if (playerRed.getScore() > playerBlue.getScore()) playerRed.setWinner(true);
        gameOver = true;
    }
}
