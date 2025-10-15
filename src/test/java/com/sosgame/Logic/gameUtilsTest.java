package com.sosgame.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class gameUtilsTest {

    private GameUtils gameUtils;

    @BeforeEach
    public void setUp() {
        gameUtils = new GameUtils();
    }

    // ---------- startNewGame() Tests ----------

    @Test
    public void testStartNewGameInitializesCorrectly() {
        gameUtils.startNewGame(5, "Simple", "Human", "Computer");

        assertNotNull(gameUtils.gameBoard);
        assertEquals(5, gameUtils.gameBoard.getSize());
        assertEquals("Simple", gameUtils.gameMode);

        assertEquals("Red", gameUtils.PlayerRed.getColor());
        assertEquals("Blue", gameUtils.PlayerBlue.getColor());

        assertEquals("Human", gameUtils.PlayerRed.getType());
        assertEquals("Computer", gameUtils.PlayerBlue.getType());

        assertTrue(gameUtils.PlayerRed.isTurn());
        assertFalse(gameUtils.PlayerBlue.isTurn());
    }

    @Test
    public void testStartNewGameResetsPreviousGame() {
        gameUtils.startNewGame(5, "General", "Human", "Human");
        gameUtils.gameBoard.placeLetter(1, 1, 'S', "Red");

        // Start a new game should reset everything
        gameUtils.startNewGame(6, "Simple", "AI", "Human");

        assertEquals(6, gameUtils.gameBoard.getSize());
        assertEquals("Simple", gameUtils.gameMode);
        assertTrue(gameUtils.PlayerRed.isTurn());
        assertFalse(gameUtils.PlayerBlue.isTurn());
        assertEquals("AI", gameUtils.PlayerRed.getType());
        assertEquals("Human", gameUtils.PlayerBlue.getType());
    }

    // ---------- SwitchTurn() Tests ----------

    @Test
    public void testSwitchTurnFromRedToBlue() {
        gameUtils.startNewGame(5, "Simple", "Human", "Human");
        gameUtils.SwitchTurn();

        assertFalse(gameUtils.PlayerRed.isTurn());
        assertTrue(gameUtils.PlayerBlue.isTurn());
    }

    @Test
    public void testSwitchTurnFromBlueToRed() {
        gameUtils.startNewGame(5, "Simple", "Human", "Human");
        gameUtils.PlayerRed.setTurn(false);
        gameUtils.PlayerBlue.setTurn(true);

        gameUtils.SwitchTurn();

        assertTrue(gameUtils.PlayerRed.isTurn());
        assertFalse(gameUtils.PlayerBlue.isTurn());
    }

    // ---------- MakeMove() Tests ----------

    @Test
    public void testMakeMoveByRedPlayer() {
        gameUtils.startNewGame(5, "Simple", "Human", "Human");

        gameUtils.PlayerRed.setSelectedLetter('S');
        gameUtils.MakeMove(0, 0);

        assertEquals('S', gameUtils.gameBoard.getletterBoard()[0][0]);
        assertEquals('R', gameUtils.gameBoard.getownerBoard()[0][0]);

        // Turn should now switch to Blue
        assertFalse(gameUtils.PlayerRed.isTurn());
        assertTrue(gameUtils.PlayerBlue.isTurn());
    }

    @Test
    public void testMakeMoveByBluePlayer() {
        gameUtils.startNewGame(5, "Simple", "Human", "Human");

        // Force Blue’s turn
        gameUtils.PlayerRed.setTurn(false);
        gameUtils.PlayerBlue.setTurn(true);
        gameUtils.PlayerBlue.setSelectedLetter('O');

        gameUtils.MakeMove(1, 1);

        assertEquals('O', gameUtils.gameBoard.getletterBoard()[1][1]);
        assertEquals('B', gameUtils.gameBoard.getownerBoard()[1][1]);

        // Turn should now switch to Red
        assertTrue(gameUtils.PlayerRed.isTurn());
        assertFalse(gameUtils.PlayerBlue.isTurn());
    }

    @Test
    public void testMakeMoveThrowsWhenInvalidCell() {
        gameUtils.startNewGame(5, "Simple", "Human", "Human");

        gameUtils.PlayerRed.setSelectedLetter('S');
        gameUtils.MakeMove(0, 0);

        gameUtils.PlayerBlue.setTurn(true);
        gameUtils.PlayerBlue.setSelectedLetter('O');

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                gameUtils.MakeMove(0, 0));

        assertEquals("Cell is already occupied.", exception.getMessage());
    }

    @Test
    public void testMakeMoveMaintainsGameIntegrity() {
        gameUtils.startNewGame(5, "General", "Human", "Human");

        gameUtils.PlayerRed.setSelectedLetter('S');
        gameUtils.MakeMove(0, 0);

        gameUtils.PlayerBlue.setSelectedLetter('O');
        gameUtils.MakeMove(0, 1);

        assertEquals('S', gameUtils.gameBoard.getletterBoard()[0][0]);
        assertEquals('O', gameUtils.gameBoard.getletterBoard()[0][1]);
        assertEquals('R', gameUtils.gameBoard.getownerBoard()[0][0]);
        assertEquals('B', gameUtils.gameBoard.getownerBoard()[0][1]);
    }
}
