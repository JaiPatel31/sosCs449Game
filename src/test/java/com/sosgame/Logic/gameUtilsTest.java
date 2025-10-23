package com.sosgame.Logic;

        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.*;

        public class gameUtilsTest {

            private Game game;

            @BeforeEach
            public void setUp() {
                game = new Game();
            }

            // ---------- startNewGame() Tests ----------

            @Test
            public void testStartNewSimpleGameInitializesCorrectly() {
                game.startNewGame(5, "Simple", "Human", "Computer");

                assertNotNull(game.getGameBoard());
                assertEquals(5, game.getGameBoard().getSize());
                assertEquals("Simple", game.getGameMode());

                assertEquals("Red", game.getPlayerRed().getColor());
                assertEquals("Blue", game.getPlayerBlue().getColor());

                assertEquals("Human", game.getPlayerRed().getType());
                assertEquals("Computer", game.getPlayerBlue().getType());

                assertTrue(game.getPlayerBlue().isTurn());
                assertFalse(game.getPlayerRed().isTurn());
            }
            @Test
            public void testStartNewGeneralGameInitializesCorrectly() {
                game.startNewGame(5, "General", "Human", "Computer");

                assertNotNull(game.getGameBoard());
                assertEquals(5, game.getGameBoard().getSize());
                assertEquals("General", game.getGameMode());

                assertEquals("Red", game.getPlayerRed().getColor());
                assertEquals("Blue", game.getPlayerBlue().getColor());

                assertEquals("Human", game.getPlayerRed().getType());
                assertEquals("Computer", game.getPlayerBlue().getType());

                assertTrue(game.getPlayerBlue().isTurn());
                assertFalse(game.getPlayerRed().isTurn());
            }
            @Test
            public void testStartNewGameResetsPreviousGame() {
                game.startNewGame(5, "General", "Human", "Human");
                game.getGameBoard().placeLetter(1, 1, 'S', "Red");

                // Start a new game should reset everything
                game.startNewGame(6, "Simple", "AI", "Human");

                assertEquals(6, game.getGameBoard().getSize());
                assertEquals("Simple", game.getGameMode());
                assertTrue(game.getPlayerBlue().isTurn());
                assertFalse(game.getPlayerRed().isTurn());
                assertEquals("AI", game.getPlayerRed().getType());
                assertEquals("Human", game.getPlayerBlue().getType());
            }

            // ---------- SwitchTurn() Tests ----------

            @Test
            public void testSwitchTurnFromBlueToRed() {
                game.startNewGame(5, "Simple", "Human", "Human");
                game.switchTurn();

                assertFalse(game.getPlayerBlue().isTurn());
                assertTrue(game.getPlayerRed().isTurn());
            }

            @Test
            public void testSwitchTurnFromRedToBlue() {
                game.startNewGame(5, "Simple", "Human", "Human");
                game.getPlayerRed().setTurn(true);
                game.getPlayerBlue().setTurn(false);

                game.switchTurn();

                assertTrue(game.getPlayerBlue().isTurn());
                assertFalse(game.getPlayerRed().isTurn());
            }

            // ---------- MakeMove() Tests ----------

            @Test
            public void testMakeMoveByRedPlayer() {
                game.startNewGame(5, "Simple", "Human", "Human");

                game.getPlayerBlue().setTurn(false);
                game.getPlayerRed().setTurn(true);

                game.getPlayerRed().setSelectedLetter('S');
                game.makeMove(0, 0);

                assertEquals('S', game.getGameBoard().getletterBoard()[0][0]);
                assertEquals('R', game.getGameBoard().getownerBoard()[0][0]);

                // Turn should now switch to Blue
                assertFalse(game.getPlayerRed().isTurn());
                assertTrue(game.getPlayerBlue().isTurn());
            }

            @Test
            public void testMakeMoveByBluePlayer() {
                game.startNewGame(5, "Simple", "Human", "Human");

                // Force Blue’s turn

                game.getPlayerBlue().setSelectedLetter('O');

                game.makeMove(1, 1);

                assertEquals('O', game.getGameBoard().getletterBoard()[1][1]);
                assertEquals('B', game.getGameBoard().getownerBoard()[1][1]);

                // Turn should now switch to Red
                assertTrue(game.getPlayerRed().isTurn());
                assertFalse(game.getPlayerBlue().isTurn());
            }

            @Test
            public void testMakeMoveThrowsWhenInvalidCell() {
                game.startNewGame(5, "Simple", "Human", "Human");

                game.getPlayerRed().setSelectedLetter('S');
                game.makeMove(0, 0);

                game.getPlayerBlue().setTurn(true);
                game.getPlayerBlue().setSelectedLetter('O');

                Exception exception = assertThrows(IllegalArgumentException.class, () ->
                        game.makeMove(0, 0));

                assertEquals("Cell is already occupied.", exception.getMessage());
            }

            @Test
            public void testMakeMoveMaintainsGameIntegrity() {
                game.startNewGame(5, "General", "Human", "Human");

                game.getPlayerBlue().setSelectedLetter('O');
                game.makeMove(0, 1);

                game.getPlayerRed().setSelectedLetter('S');
                game.makeMove(0, 0);



                assertEquals('S', game.getGameBoard().getletterBoard()[0][0]);
                assertEquals('O', game.getGameBoard().getletterBoard()[0][1]);
                assertEquals('R', game.getGameBoard().getownerBoard()[0][0]);
                assertEquals('B', game.getGameBoard().getownerBoard()[0][1]);
            }
        }