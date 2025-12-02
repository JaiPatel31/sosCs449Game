package com.sosgame.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComputerPlayerTest {

    private GameBoard board;
    private ComputerPlayer computerPlayer;
    private HumanPlayer humanPlayer;

    @BeforeEach
    void setUp() {
        board = new GameBoard(3);
        computerPlayer = new ComputerPlayer("Blue");
        humanPlayer = new HumanPlayer("Red");
    }

    // AC 8.1 & 9.1: Game starts with player and computer
    @Test
    void testGameStartsWithPlayerAndComputer() {
        SimpleGame game = new SimpleGame(board, humanPlayer, computerPlayer);
        game.initialize();

        assertNotNull(game.getPlayerRed());
        assertNotNull(game.getPlayerBlue());
        assertTrue(game.getPlayerBlue() instanceof ComputerPlayer);
    }

    // AC 8.2 & 9.2: Computer takes turns automatically
    @Test
    void testComputerTakesAutomaticTurns() {
        computerPlayer.setTurn(true);
        int[] move = computerPlayer.chooseMove(board);

        assertNotNull(move);
        assertEquals(2, move.length);
        assertTrue(move[0] >= 0 && move[0] < 3);
        assertTrue(move[1] >= 0 && move[1] < 3);
    }

    // AC 8.3 & 9.3: Computer move is valid
    @Test
    void testComputerMoveIsValid() {
        // Occupy two cells
        board.placeLetter(0, 0, 'S', "Red");
        board.placeLetter(0, 1, 'O', "Blue");

        int[] move = computerPlayer.chooseMove(board);

        assertNotNull(move);
        // Ensure move is not on occupied cells
        assertFalse(move[0] == 0 && move[1] == 0);
        assertFalse(move[0] == 0 && move[1] == 1);
        assertTrue(board.isCellEmpty(move[0], move[1]));
    }

    // AC 8.4: Turn indication
    @Test
    void testTurnIndicationChanges() {
        SimpleGame game = new SimpleGame(board, humanPlayer, computerPlayer);
        game.initialize();

        computerPlayer.setTurn(true);
        humanPlayer.setTurn(false);
        assertTrue(computerPlayer.isTurn());
        assertFalse(humanPlayer.isTurn());

        computerPlayer.setTurn(false);
        humanPlayer.setTurn(true);
        assertFalse(computerPlayer.isTurn());
        assertTrue(humanPlayer.isTurn());
    }

    // AC 8.5 & 9.5: Game completion and winner determination
    @Test
    void testGameCompletionSimpleMode() {
        SimpleGame game = new SimpleGame(board, humanPlayer, computerPlayer);
        game.initialize();

        // Fill board without SOS
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Player currentPlayer = humanPlayer.isTurn() ? humanPlayer : computerPlayer;
                currentPlayer.setSelectedLetter('S');
                game.makeMove(r, c);

            }
        }

        assertTrue(board.isBoardFull());
        assertTrue(game.isGameOver());
    }

    // AC 9.4: Scoring updates when SOS formed
    @Test
    void testScoringUpdatesOnSOS() {
        GeneralGame game = new GeneralGame(board, humanPlayer, computerPlayer);
        game.initialize();

        humanPlayer.setTurn(true);
        game.makeMove(0, 0 );
        humanPlayer.setSelectedLetter('O');
        game.makeMove(0, 1);
        humanPlayer.setSelectedLetter('S');
        game.makeMove(0, 2);

        int redScore = humanPlayer.getScore();
        int blueScore = computerPlayer.getScore();
        assertTrue(redScore > 0 || blueScore > 0, "Expected at least one player to score from SOS");
    }

    // AC 10.1: Computer vs Computer starts automatically
    @Test
    void testComputerVsComputerStartsAutomatically() {
        ComputerPlayer computerRed = new ComputerPlayer("Red");
        ComputerPlayer computerBlue = new ComputerPlayer("Blue");

        SimpleGame game = new SimpleGame(board, computerRed, computerBlue);
        game.initialize();

        assertTrue(computerRed instanceof ComputerPlayer);
        assertTrue(computerBlue instanceof ComputerPlayer);
        assertNotNull(game);
    }

    // AC 10.2 & 10.3: Automated turns with valid moves
    @Test
    void testAutomatedTurnsAreValid() {
        ComputerPlayer computerRed = new ComputerPlayer("Red");
        ComputerPlayer computerBlue = new ComputerPlayer("Blue");

        // Blue plays first
        computerBlue.setTurn(true);
        int[] blueMove = computerBlue.chooseMove(board);
        board.placeLetter(blueMove[0], blueMove[1], 'S', "Blue");

        // Red plays next
        computerBlue.setTurn(false);
        computerRed.setTurn(true);
        int[] redMove = computerRed.chooseMove(board);
        board.placeLetter(redMove[0], redMove[1], 'O', "Red");

        // Verify moves are different and valid
        assertNotNull(redMove);
        assertTrue(board.isCellEmpty(blueMove[0], blueMove[1]) == false);
        assertTrue(board.isCellEmpty(redMove[0], redMove[1]) == false);
        assertFalse(blueMove[0] == redMove[0] && blueMove[1] == redMove[1]);
    }

    // AC 10.5: Game stops automatically when complete
    @Test
    void testGameStopsWhenComplete() {
        SimpleGame game = new SimpleGame(board, computerPlayer, humanPlayer);
        game.initialize();

        // Fill entire board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Player currentPlayer = humanPlayer.isTurn() ? humanPlayer : computerPlayer;
                currentPlayer.setSelectedLetter('S');
                game.makeMove(r, c);

            }
        }

        assertTrue(board.isBoardFull());
        assertTrue(game.isGameOver());
    }

    // AC 10.6: New game starts from empty board
    @Test
    void testNewGameStartsFromEmptyBoard() {
        board.placeLetter(0, 0, 'S', "Red");
        board.placeLetter(1, 1, 'O', "Blue");

        GameBoard newBoard = new GameBoard(3);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertTrue(newBoard.isCellEmpty(r, c));
            }
        }
    }
}
