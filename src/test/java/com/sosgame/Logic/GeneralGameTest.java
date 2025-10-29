package com.sosgame.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralGameTest {

    private GameBoard board;
    private Player red, blue;
    private GeneralGame game;

    @BeforeEach
    void setUp() {
        board = new GameBoard(3);
        red = new Player("Red", "Human");
        blue = new Player("Blue", "Human");
        game = new GeneralGame(board, red, blue);
        game.initialize();
    }

    @Test
    void testPlayerScoresOnSOS() {
        // Arrange
        GameBoard board = new GameBoard(3);
        Player red  = new Player("Red", "Human");
        Player blue = new Player("Blue", "Human");
        GeneralGame game = new GeneralGame(board, red, blue);
        game.initialize(); // Blue starts

        // Pre-set row 0 so that placing S at (0,2) completes S-O-S
        board.placeLetter(0, 0, 'S', "Red");
        board.placeLetter(0, 1, 'O', "Red");

        // Act
        blue.setSelectedLetter('S');
        game.makeMove(0, 2);

        // Assert
        assertEquals(1, blue.getScore(), "Blue should earn 1 point for forming SOS");
        assertFalse(game.isGameOver(), "General game should continue after SOS");
        assertTrue(blue.isTurn(), "Blue keeps the turn after scoring");
    }


    @Test
    void testTurnSwitchesWhenNoSOS() {
        blue.setSelectedLetter('S');
        game.makeMove(1, 1);
        assertTrue(red.isTurn());
        assertFalse(blue.isTurn());
    }

    @Test
    void testMultipleSOSAddsMultiplePoints() {

        // Prepare S's around the center (1,1)
        board.placeLetter(0, 1, 'S', "Blue"); // above
        board.placeLetter(2, 1, 'S', "Blue"); // below
        board.placeLetter(1, 0, 'S', "Blue"); // left
        board.placeLetter(1, 2, 'S', "Blue"); // right

        // Act: placing 'O' in the center should create 2 SOS (vertical + horizontal)
        blue.setSelectedLetter('O');
        game.makeMove(1, 1);

        // Assert
        assertEquals(2, blue.getScore(), "Blue should score 2 points (vertical + horizontal SOS)");
        assertTrue(blue.isTurn(), "Blue should keep the turn after scoring");
        assertFalse(game.isGameOver(), "Game should not end yet in general mode");
    }


    @Test
    void testGameEndsWhenBoardFull() {
        // Fill all but one cell
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (!(r == 2 && c == 2)) { // leave bottom-right empty
                    board.placeLetter(r, c, 'S', "Red");
                }
            }
        }

        // Last move fills the board and should trigger finishGame()
        blue.setSelectedLetter('O');
        game.makeMove(2, 2);

        assertTrue(game.isGameOver(), "Game should end when the last cell is filled");
    }


    @Test
    void testWinnerDeterminedByScore() {
        // Arrange
        GameBoard board = new GameBoard(3);
        Player red = new Player("Red", "Human");
        Player blue = new Player("Blue", "Human");
        GeneralGame game = new GeneralGame(board, red, blue);
        game.initialize();

        // Give Blue a higher score
        blue.incrementScoreByNumber(2);
        red.incrementScore();

        // Fill all but one cell (leave bottom-right empty)
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if (r == 2 && c == 2) continue;
                board.placeLetter(r, c, 'S', "Red");
            }
        }

        // Act: final move fills last cell → triggers finishGame()
        blue.setSelectedLetter('O');
        game.makeMove(2, 2);

        // Assert
        assertTrue(game.isGameOver(), "Game should end when board becomes full");
        assertTrue(blue.isWinner(), "Blue should be the winner (higher score)");
    }

    @Test
    void testGameEndsInDrawWhenScoresEqual() {
        // Arrange
        GameBoard board = new GameBoard(3);
        Player red = new Player("Red", "Human");
        Player blue = new Player("Blue", "Human");
        GeneralGame game = new GeneralGame(board, red, blue);
        game.initialize();

        // Both players have equal scores
        red.incrementScore();
        blue.incrementScore();

        // Fill all but one cell (leave bottom-right empty)
        for (int r = 0; r < board.getSize(); r++) {
            for (int c = 0; c < board.getSize(); c++) {
                if (r == 2 && c == 2) continue;
                board.placeLetter(r, c, 'S', "Red");
            }
        }

        // Act: last move fills the board
        blue.setSelectedLetter('O');
        game.makeMove(2, 2);

        // Assert
        assertTrue(game.isGameOver(), "Game should end when board is full");
        assertFalse(red.isWinner(), "No player should be declared the winner (draw)");
        assertFalse(blue.isWinner(), "No player should be declared the winner (draw)");
    }
}