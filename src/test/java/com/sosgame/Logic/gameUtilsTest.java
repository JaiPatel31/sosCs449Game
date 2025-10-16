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
            public void testStartNewSimpleGameInitializesCorrectly() {
                gameUtils.startNewGame(5, "Simple", "Human", "Computer");

                assertNotNull(gameUtils.getGameBoard());
                assertEquals(5, gameUtils.getGameBoard().getSize());
                assertEquals("Simple", gameUtils.getGameMode());

                assertEquals("Red", gameUtils.getPlayerRed().getColor());
                assertEquals("Blue", gameUtils.getPlayerBlue().getColor());

                assertEquals("Human", gameUtils.getPlayerRed().getType());
                assertEquals("Computer", gameUtils.getPlayerBlue().getType());

                assertTrue(gameUtils.getPlayerRed().isTurn());
                assertFalse(gameUtils.getPlayerBlue().isTurn());
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

                assertTrue(gameUtils.getPlayerRed().isTurn());
                assertFalse(gameUtils.getPlayerBlue().isTurn());
            }
            @Test
            public void testStartNewGameResetsPreviousGame() {
                gameUtils.startNewGame(5, "General", "Human", "Human");
                gameUtils.getGameBoard().placeLetter(1, 1, 'S', "Red");

                // Start a new game should reset everything
                gameUtils.startNewGame(6, "Simple", "AI", "Human");

                assertEquals(6, gameUtils.getGameBoard().getSize());
                assertEquals("Simple", gameUtils.getGameMode());
                assertTrue(gameUtils.getPlayerRed().isTurn());
                assertFalse(gameUtils.getPlayerBlue().isTurn());
                assertEquals("AI", gameUtils.getPlayerRed().getType());
                assertEquals("Human", gameUtils.getPlayerBlue().getType());
            }

            // ---------- SwitchTurn() Tests ----------

            @Test
            public void testSwitchTurnFromRedToBlue() {
                gameUtils.startNewGame(5, "Simple", "Human", "Human");
                gameUtils.SwitchTurn();

                assertFalse(gameUtils.getPlayerRed().isTurn());
                assertTrue(gameUtils.getPlayerBlue().isTurn());
            }

            @Test
            public void testSwitchTurnFromBlueToRed() {
                gameUtils.startNewGame(5, "Simple", "Human", "Human");
                gameUtils.getPlayerRed().setTurn(false);
                gameUtils.getPlayerBlue().setTurn(true);

                gameUtils.SwitchTurn();

                assertTrue(gameUtils.getPlayerRed().isTurn());
                assertFalse(gameUtils.getPlayerBlue().isTurn());
            }

            // ---------- MakeMove() Tests ----------

            @Test
            public void testMakeMoveByRedPlayer() {
                gameUtils.startNewGame(5, "Simple", "Human", "Human");

                gameUtils.getPlayerRed().setSelectedLetter('S');
                gameUtils.MakeMove(0, 0);

                assertEquals('S', gameUtils.getGameBoard().getletterBoard()[0][0]);
                assertEquals('R', gameUtils.getGameBoard().getownerBoard()[0][0]);

                // Turn should now switch to Blue
                assertFalse(gameUtils.getPlayerRed().isTurn());
                assertTrue(gameUtils.getPlayerBlue().isTurn());
            }

            @Test
            public void testMakeMoveByBluePlayer() {
                gameUtils.startNewGame(5, "Simple", "Human", "Human");

                // Force Blue’s turn
                gameUtils.getPlayerRed().setTurn(false);
                gameUtils.getPlayerBlue().setTurn(true);
                gameUtils.getPlayerBlue().setSelectedLetter('O');

                gameUtils.MakeMove(1, 1);

                assertEquals('O', gameUtils.getGameBoard().getletterBoard()[1][1]);
                assertEquals('B', gameUtils.getGameBoard().getownerBoard()[1][1]);

                // Turn should now switch to Red
                assertTrue(gameUtils.getPlayerRed().isTurn());
                assertFalse(gameUtils.getPlayerBlue().isTurn());
            }

            @Test
            public void testMakeMoveThrowsWhenInvalidCell() {
                gameUtils.startNewGame(5, "Simple", "Human", "Human");

                gameUtils.getPlayerRed().setSelectedLetter('S');
                gameUtils.MakeMove(0, 0);

                gameUtils.getPlayerBlue().setTurn(true);
                gameUtils.getPlayerBlue().setSelectedLetter('O');

                Exception exception = assertThrows(IllegalArgumentException.class, () ->
                        gameUtils.MakeMove(0, 0));

                assertEquals("Cell is already occupied.", exception.getMessage());
            }

            @Test
            public void testMakeMoveMaintainsGameIntegrity() {
                gameUtils.startNewGame(5, "General", "Human", "Human");

                gameUtils.getPlayerRed().setSelectedLetter('S');
                gameUtils.MakeMove(0, 0);

                gameUtils.getPlayerBlue().setSelectedLetter('O');
                gameUtils.MakeMove(0, 1);

                assertEquals('S', gameUtils.getGameBoard().getletterBoard()[0][0]);
                assertEquals('O', gameUtils.getGameBoard().getletterBoard()[0][1]);
                assertEquals('R', gameUtils.getGameBoard().getownerBoard()[0][0]);
                assertEquals('B', gameUtils.getGameBoard().getownerBoard()[0][1]);
            }
        }