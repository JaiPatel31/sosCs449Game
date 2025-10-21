package com.sosgame.Logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testConstructor() {
        Player player = new Player("Red", "Human");
        assertEquals("Red", player.getColor());
        assertEquals("Human", player.getType());
        assertEquals(0, player.getScore());
        assertEquals('S', player.getSelectedLetter());
        assertFalse(player.isTurn());
        assertFalse(player.isWinner());
    }

    @Test
    void testIncrementScore() {
        Player player = new Player("Blue", "Computer");
        player.incrementScore();
        assertEquals(1, player.getScore());
        player.incrementScoreByNumber(3);
        assertEquals(4, player.getScore());
    }

    @Test
    void testSetSelectedLetter_Valid() {
        Player player = new Player("Red", "Human");
        player.setSelectedLetter('O');
        assertEquals('O', player.getSelectedLetter());
        player.setSelectedLetter('S');
        assertEquals('S', player.getSelectedLetter());
    }

    @Test
    void testSetSelectedLetter_Invalid() {
        Player player = new Player("Blue", "Computer");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            player.setSelectedLetter('X');
        });
        assertEquals("Invalid letter. Only 'S' or 'O' are allowed.", exception.getMessage());
    }

    @Test
    void testSetTurn() {
        Player player = new Player("Red", "Human");
        player.setTurn(true);
        assertTrue(player.isTurn());
        player.setTurn(false);
        assertFalse(player.isTurn());
    }

    @Test
    void testSetWinner() {
        Player player = new Player("Blue", "Computer");
        player.isWinner = true; // Directly setting for test purposes
        assertTrue(player.isWinner());
    }


}