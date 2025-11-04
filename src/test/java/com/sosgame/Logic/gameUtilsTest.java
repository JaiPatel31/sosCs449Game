package com.sosgame.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GameUtils, SimpleGame, and GeneralGame integration.
 */
public class gameUtilsTest {

    private GameUtils gameUtils;

    @BeforeEach
    public void setUp() {
        gameUtils = new GameUtils();
    }

    // ---------- startNewGame() Tests ----------

    @Test
    public void testStartNewSimpleGameInitializesCorrectly() {
        gameUtils.startNewGame(5, "Simple", "Human", "Computer");

        assertNotNull(gameUtils.getGameBoard());
        assertEquals(5, gameUtils.getGameBoard().getSize());
        assertEquals("Simple", gameUtils.getGameMode());

        assertEquals("Red", gameUtils.getPlayerRed().getColor());
        assertEquals("Blue", gameUtils.getPlayerBlue().getColor());

        assertEquals("Human", gameUtils.getPlayerRed().getType());
        assertEquals("Computer", gameUtils.getPlayerBlue().getType());

        assertTrue(gameUtils.getPlayerBlue().isTurn());
        assertFalse(gameUtils.getPlayerRed().isTurn());
    }

    @Test
    public void testStartNewGeneralGameInitializesCorrectly() {
        gameUtils.startNewGame(5, "General", "Human", "Computer");

        assertNotNull(gameUtils.getGameBoard());
        assertEquals(5, gameUtils.getGameBoard().getSize());
        assertEquals("General", gameUtils.getGameMode());

        assertEquals("Red", gameUtils.getPlayerRed().getColor());
        assertEquals("Blue", gameUtils.getPlayerBlue().getColor());

        assertEquals("Human", gameUtils.getPlayerRed().getType());
        assertEquals("Computer", gameUtils.getPlayerBlue().getType());

        assertTrue(gameUtils.getPlayerBlue().isTurn());
        assertFalse(gameUtils.getPlayerRed().isTurn());
    }

    @Test
    public void testStartNewGameResetsPreviousGame() {
        gameUtils.startNewGame(5, "General", "Human", "Human");
        gameUtils.getGameBoard().placeLetter(1, 1, 'S', "Red");

        // Start a new game should reset everything
        gameUtils.startNewGame(6, "Simple", "AI", "Human");

        assertEquals(6, gameUtils.getGameBoard().getSize());
        assertEquals("Simple", gameUtils.getGameMode());
        assertTrue(gameUtils.getPlayerBlue().isTurn());
        assertFalse(gameUtils.getPlayerRed().isTurn());
        assertEquals("AI", gameUtils.getPlayerRed().getType());
        assertEquals("Human", gameUtils.getPlayerBlue().getType());
    }

    // ---------- SwitchTurn() Tests ----------
    // switchTurn() is private now, so test via move sequence

    @Test
    public void testTurnSwitchesFromBlueToRedAfterMove() {
        gameUtils.startNewGame(3, "Simple", "Human", "Human");

        gameUtils.getPlayerBlue().setSelectedLetter('S');
        gameUtils.makeMove(0, 0);

        assertTrue(gameUtils.getPlayerRed().isTurn());
        assertFalse(gameUtils.getPlayerBlue().isTurn());
    }

    @Test
    public void testTurnSwitchesFromRedToBlueAfterMove() {
        gameUtils.startNewGame(3, "Simple", "Human", "Human");

        // Force Red’s turn
        gameUtils.getPlayerRed().setTurn(true);
        gameUtils.getPlayerBlue().setTurn(false);

        gameUtils.getPlayerRed().setSelectedLetter('S');
        gameUtils.makeMove(0, 0);

        assertTrue(gameUtils.getPlayerBlue().isTurn());
        assertFalse(gameUtils.getPlayerRed().isTurn());
    }

    // ---------- MakeMove() Tests ----------

    @Test
    public void testMakeMoveByRedPlayerPlacesCorrectLetterAndOwner() {
        gameUtils.startNewGame(3, "Simple", "Human", "Human");

        gameUtils.getPlayerBlue().setTurn(false);
        gameUtils.getPlayerRed().setTurn(true);
        gameUtils.getPlayerRed().setSelectedLetter('S');

        gameUtils.makeMove(0, 0);

        assertEquals('S', gameUtils.getGameBoard().getLetterAt(0, 0));
        assertEquals('R', gameUtils.getGameBoard().getOwnerAt(0, 0));

        // Turn should now switch to Blue
        assertFalse(gameUtils.getPlayerRed().isTurn());
        assertTrue(gameUtils.getPlayerBlue().isTurn());
    }

    @Test
    public void testMakeMoveByBluePlayerPlacesCorrectLetterAndOwner() {
        gameUtils.startNewGame(3, "Simple", "Human", "Human");

        gameUtils.getPlayerBlue().setSelectedLetter('O');
        gameUtils.makeMove(1, 1);

        assertEquals('O', gameUtils.getGameBoard().getLetterAt(1, 1));
        assertEquals('B', gameUtils.getGameBoard().getOwnerAt(1, 1));

        // Turn should now switch to Red
        assertTrue(gameUtils.getPlayerRed().isTurn());
        assertFalse(gameUtils.getPlayerBlue().isTurn());
    }

    @Test
    public void testMakeMoveThrowsWhenInvalidCell() {
        gameUtils.startNewGame(3, "Simple", "Human", "Human");

        gameUtils.getPlayerBlue().setSelectedLetter('S');
        gameUtils.makeMove(0, 0);

        gameUtils.getPlayerRed().setSelectedLetter('O');
        assertThrows(IllegalArgumentException.class,
                () -> gameUtils.makeMove(0, 0),
                "Cell is already occupied.");
    }

    @Test
    public void testMakeMoveMaintainsGameIntegrity() {
        gameUtils.startNewGame(3, "General", "Human", "Human");

        gameUtils.getPlayerBlue().setSelectedLetter('O');
        gameUtils.makeMove(0, 1);

        gameUtils.getPlayerRed().setSelectedLetter('S');
        gameUtils.makeMove(0, 0);

        assertEquals('S', gameUtils.getGameBoard().getLetterAt(0, 0));
        assertEquals('O', gameUtils.getGameBoard().getLetterAt(0, 1));
        assertEquals('R', gameUtils.getGameBoard().getOwnerAt(0, 0));
        assertEquals('B', gameUtils.getGameBoard().getOwnerAt(0, 1));
    }
}
