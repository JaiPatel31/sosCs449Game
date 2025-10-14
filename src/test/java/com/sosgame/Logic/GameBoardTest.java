package com.sosgame.Logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @Test
    void testConstructor_ValidSize() {
        GameBoard board = new GameBoard(5);
        assertEquals(5, board.getSize());
        assertEquals(" ", board.getBoard()[0][0]);
    }

    @Test
    void testConstructor_InvalidSize() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new GameBoard(4);
        });
        assertEquals("Board size must be between 3 and 10.", exception.getMessage());
    }

    @Test
    void testConstructor_ValidBoard() {
        String[][] initialBoard = {
                {"SR", " ", " ", " ", " "},
                {" ", "OB", " ", " ", " "},
                {" ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " "}
        };
        GameBoard board = new GameBoard(initialBoard);
        assertEquals(5, board.getSize());
        assertEquals("SR", board.getBoard()[0][0]);
        assertEquals("OB", board.getBoard()[1][1]);
    }

    @Test
    void testPlaceLetter_ValidMove() {
        GameBoard board = new GameBoard(5);
        board.placeLetter(0, 0, 'S', "Red");
        assertEquals("SR", board.getBoard()[0][0]);
    }

    @Test
    void testPlaceLetter_InvalidMove_CellOccupied() {
        GameBoard board = new GameBoard(5);
        board.placeLetter(0, 0, 'S', "Red");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            board.placeLetter(0, 0, 'O', "Blue");
        });
        assertEquals("Cell is already occupied.", exception.getMessage());
    }

    @Test
    void testPlaceLetter_InvalidMove_InvalidLetter() {
        GameBoard board = new GameBoard(5);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            board.placeLetter(0, 0, 'X', "Red");
        });
        assertEquals("Invalid letter. Only 'S' or 'O' are allowed.", exception.getMessage());
    }

    @Test
    void testPlaceLetter_InvalidMove_InvalidPlayer() {
        GameBoard board = new GameBoard(5);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            board.placeLetter(0, 0, 'S', "Green");
        });
        assertEquals("Invalid player. Only 'Red' or 'Blue' are allowed.", exception.getMessage());
    }


}