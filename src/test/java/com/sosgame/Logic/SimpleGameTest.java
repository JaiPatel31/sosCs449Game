package com.sosgame.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleGameTest {

    private GameBoard board;
    private Player red, blue;
    private SimpleGame game;

    @BeforeEach
    void setUp() {
        board = new GameBoard(3);
        red = new Player("Red", "Human");
        blue = new Player("Blue", "Human");
        game = new SimpleGame(board, red, blue);
        game.initialize();
    }

    @Test
    void testGameEndsWhenSOSFormed() {
        // Blue makes "S O S" horizontally on top row
        blue.setSelectedLetter('S');
        game.makeMove(0, 0);
        red.setSelectedLetter('O');
        game.makeMove(0, 1);
        blue.setSelectedLetter('S');
        game.makeMove(0, 2);

        assertTrue(game.isGameOver(), "Simple game should end when SOS formed");
        assertTrue(blue.isWinner(), "Blue should be the winner");
    }

    @Test
    void testNoWinSwitchTurns() {
        blue.setSelectedLetter('S');
        game.makeMove(1, 1);

        assertTrue(red.isTurn(), "Turn should switch to Red");
        assertFalse(blue.isTurn());
        assertFalse(game.isGameOver());
    }

    @Test
    void testInitialTurnIsBlue() {
        assertTrue(blue.isTurn());
        assertFalse(red.isTurn());
    }

    @Test
    void testCannotPlaceOnOccupiedCell() {
        blue.setSelectedLetter('S');
        game.makeMove(0, 0);

        red.setSelectedLetter('O');
        assertThrows(IllegalArgumentException.class,
                () -> game.makeMove(0, 0),
                "Cell is already occupied.");
    }

    @Test
    void testWinnerSetProperly() {
        // Blue makes "S O S"
        blue.setSelectedLetter('S'); game.makeMove(0, 0);
        red.setSelectedLetter('O');  game.makeMove(0, 1);
        blue.setSelectedLetter('S'); game.makeMove(0, 2);

        assertTrue(blue.isWinner());
        assertTrue(game.isGameOver());
    }

    @Test
    void testGameEndsInTieNoSOSFormed() {
        // Fill the board with moves that do NOT form SOS
        // Row 0
        blue.setSelectedLetter('S');
        game.makeMove(0, 0);
        red.setSelectedLetter('O');
        game.makeMove(0, 1);
        blue.setSelectedLetter('O');
        game.makeMove(0, 2);

        // Row 1
        red.setSelectedLetter('O');
        game.makeMove(1, 0);
        blue.setSelectedLetter('S');
        game.makeMove(1, 1);
        red.setSelectedLetter('O');
        game.makeMove(1, 2);

        // Row 2
        blue.setSelectedLetter('O');
        game.makeMove(2, 0);
        red.setSelectedLetter('S');
        game.makeMove(2, 1);
        blue.setSelectedLetter('O');
        game.makeMove(2, 2);

        // Assertions
        assertTrue(game.isGameOver(), "Game should end when the board is full");
        assertFalse(blue.isWinner(), "No player should be declared the winner");
        assertFalse(red.isWinner(), "No player should be declared the winner");
    }
}

