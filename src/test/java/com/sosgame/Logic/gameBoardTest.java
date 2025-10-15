package com.sosgame.Logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class gameBoardTest {

    // Constructor Tests
    @Test
    public void testValidBoardInitialization() {
        GameBoard board = new GameBoard(5);
        assertEquals(5, board.getSize());
        assertEquals(' ', board.getletterBoard()[0][0]);
        assertEquals(' ', board.getownerBoard()[0][0]);
    }

    @Test
    public void testInvalidBoardSizeTooSmall() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GameBoard(4));
        assertEquals("Board size must be between 5 and 11.", exception.getMessage());
    }

    @Test
    public void testInvalidBoardSizeTooLarge() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GameBoard(12));
        assertEquals("Board size must be between 5 and 11.", exception.getMessage());
    }

    @Test
    public void testCopyConstructorCreatesDeepCopy() {
        GameBoard original = new GameBoard(5);
        original.placeLetter(2, 2, 'S', "Red");

        GameBoard copy = new GameBoard(original);
        assertNotSame(original.getletterBoard(), copy.getletterBoard());
        assertEquals('S', copy.getletterBoard()[2][2]);
        assertEquals('R', copy.getownerBoard()[2][2]);
    }

    //Cell Tests
    @Test
    public void testIsCellEmptyInitiallyTrue() {
        GameBoard board = new GameBoard(5);
        assertTrue(board.isCellEmpty(0, 0));
    }

    @Test
    public void testIsCellEmptyAfterPlacingLetterFalse() {
        GameBoard board = new GameBoard(5);
        board.placeLetter(1, 1, 'S', "Blue");
        assertFalse(board.isCellEmpty(1, 1));
    }

    //placeletter tests
    @Test
    public void testPlaceLetterValidRed() {
        GameBoard board = new GameBoard(5);
        board.placeLetter(0, 0, 'S', "Red");
        assertEquals('S', board.getletterBoard()[0][0]);
        assertEquals('R', board.getownerBoard()[0][0]);
    }

    @Test
    public void testPlaceLetterValidBlue() {
        GameBoard board = new GameBoard(5);
        board.placeLetter(0, 1, 'O', "Blue");
        assertEquals('O', board.getletterBoard()[0][1]);
        assertEquals('B', board.getownerBoard()[0][1]);
    }

    @Test
    public void testPlaceLetterOnOccupiedCellThrowsException() {
        GameBoard board = new GameBoard(5);
        board.placeLetter(2, 2, 'S', "Red");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                board.placeLetter(2, 2, 'O', "Blue"));
        assertEquals("Cell is already occupied.", exception.getMessage());
    }

    @Test
    public void testPlaceLetterInvalidLetterThrowsException() {
        GameBoard board = new GameBoard(5);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                board.placeLetter(1, 1, 'X', "Red"));
        assertEquals("Invalid letter. Only 'S' or 'O' are allowed.", exception.getMessage());
    }

    @Test
    public void testPlaceLetterInvalidPlayerThrowsException() {
        GameBoard board = new GameBoard(5);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                board.placeLetter(1, 1, 'S', "Green"));
        assertEquals("Invalid player. Only 'Red' or 'Blue' are allowed.", exception.getMessage());
    }

    // Test Boundary Conditions
    @Test
    public void testPlaceLetterAtEdgeOfBoard() {
        GameBoard board = new GameBoard(5);
        board.placeLetter(4, 4, 'O', "Blue");
        assertEquals('O', board.getletterBoard()[4][4]);
        assertEquals('B', board.getownerBoard()[4][4]);
    }

    @Test
    public void testCopyBoardAfterModificationDoesNotAffectOriginal() {
        GameBoard board = new GameBoard(5);
        board.placeLetter(0, 0, 'S', "Red");
        GameBoard copy = new GameBoard(board);
        copy.placeLetter(1, 1, 'O', "Blue");

        // Original should not be affected by changes in copy
        assertEquals(' ', board.getletterBoard()[1][1]);
        assertEquals('O', copy.getletterBoard()[1][1]);
    }
}
