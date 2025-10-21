package com.sosgame.UI;

import com.sosgame.Logic.GameUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class gameUITest {

    private gameUI gameUIInstance;
    private TopMenu mockTopMenu;
    private LeftMenu mockLeftMenu;
    private RightMenu mockRightMenu;
    private GameBoardUI mockGameBoardUI;
    private GameUtils mockGameUtils;

    @BeforeAll
    static void initToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // already started, ignore
        }
    }
    static void setupEnvironment() {
        System.setProperty("net.bytebuddy.agent.silent", "true");
        try {
            javafx.embed.swing.JFXPanel fxPanel = new javafx.embed.swing.JFXPanel();
        } catch (Exception ignored) {
        }
    }

    @BeforeEach
    public void setUp() {
        // Prepare the test subject and mocks
        gameUIInstance = new gameUI();
        mockTopMenu = mock(TopMenu.class);
        mockLeftMenu = mock(LeftMenu.class);
        mockRightMenu = mock(RightMenu.class);
        mockGameBoardUI = mock(GameBoardUI.class);
        mockGameUtils = mock(GameUtils.class);

        // Inject mocks using reflection (since fields are private)
        try {
            var topField = gameUI.class.getDeclaredField("topMenu");
            topField.setAccessible(true);
            topField.set(gameUIInstance, mockTopMenu);

            var leftField = gameUI.class.getDeclaredField("leftMenu");
            leftField.setAccessible(true);
            leftField.set(gameUIInstance, mockLeftMenu);

            var rightField = gameUI.class.getDeclaredField("rightMenu");
            rightField.setAccessible(true);
            rightField.set(gameUIInstance, mockRightMenu);

            var gameBoardField = gameUI.class.getDeclaredField("gameBoardUI");
            gameBoardField.setAccessible(true);
            gameBoardField.set(gameUIInstance, mockGameBoardUI);

            var gameUtilsField = gameUI.class.getDeclaredField("gameUtils");
            gameUtilsField.setAccessible(true);
            gameUtilsField.set(gameUIInstance, mockGameUtils);

            var rootField = gameUI.class.getDeclaredField("root");
            rootField.setAccessible(true);
            rootField.set(gameUIInstance, new BorderPane());
        } catch (Exception e) {
            fail("Reflection injection failed: " + e.getMessage());
        }
    }

    // ---------- createGUI() Tests ----------

    @Test
    public void testCreateGUIInitializesLayoutAndComponents() {
        gameUI ui = new gameUI();
        BorderPane root = ui.createGUI();

        assertNotNull(root);
        assertNotNull(root.getTop(), "Top menu should be initialized");
        assertNotNull(root.getLeft(), "Left menu should be initialized");
        assertNotNull(root.getRight(), "Right menu should be initialized");
        assertNotNull(root.getCenter(), "Game board should be set after starting game");
    }

    // ---------- startNewGame() Success Path ----------

    // Test that starting a new game calls GameUtils with correct parameters and sets the game board in the center
    @Test
    public void testStartNewGameSuccessfullyCallsGameUtilsAndSetsCenter() {
        // Set up mock return values for menu selections
        when(mockTopMenu.getBoardSize()).thenReturn(5); // Board size
        when(mockTopMenu.getMode()).thenReturn("Simple"); // Game mode
        when(mockRightMenu.getRedType()).thenReturn("Human"); // Red player type
        when(mockLeftMenu.getBlueType()).thenReturn("Computer"); // Blue player type
        when(mockGameBoardUI.createGameBoard(anyInt(), any())).thenReturn(new VBox()); // Game board UI

        // Call the method to start a new game
        gameUIInstance.startNewGame();

        // Verify that GameUtils.startNewGame was called with correct arguments
        verify(mockGameUtils, times(1)).startNewGame(5, "Simple", "Human", "Computer");
        // Check that the game board is set in the center of the UI
        assertNotNull(((BorderPane) getPrivateField(gameUIInstance, "root")).getCenter());
    }

    @Test
    public void testGameModeDefaultsToSimpleInUI() {
        when(mockTopMenu.getMode()).thenReturn("Simple");
        when(mockTopMenu.getBoardSize()).thenReturn(5);
        when(mockRightMenu.getRedType()).thenReturn("Human");
        when(mockLeftMenu.getBlueType()).thenReturn("Human");
        when(mockGameBoardUI.createGameBoard(anyInt(), any())).thenReturn(new VBox());

        gameUIInstance.startNewGame();

        verify(mockGameUtils).startNewGame(5, "Simple","Human", "Human");
    }

    @Test
    public void testStartNewGameResetsPreviousGameState() {
        when(mockTopMenu.getBoardSize()).thenReturn(7);
        when(mockTopMenu.getMode()).thenReturn("General");
        when(mockRightMenu.getRedType()).thenReturn("Human");
        when(mockLeftMenu.getBlueType()).thenReturn("Computer");
        VBox newBoard = new VBox();
        when(mockGameBoardUI.createGameBoard(anyInt(), any())).thenReturn(newBoard);

        BorderPane root = (BorderPane) getPrivateField(gameUIInstance, "root");
        VBox oldBoard = new VBox();
        Platform.runLater(() -> {
            root.setCenter(oldBoard);
            gameUIInstance.startNewGame();
            // After starting new game, the center should be replaced
            assertEquals(newBoard, root.getCenter());
            verify(mockGameUtils).startNewGame(7,"General", "Human", "Computer" );
        });

        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    // ---------- startNewGame() Failure Path ----------

    @Test
    public void testStartNewGameShowsAlertOnInvalidSize() {
        when(mockTopMenu.getBoardSize()).thenReturn(3); // invalid
        when(mockTopMenu.getMode()).thenReturn("Simple");
        when(mockRightMenu.getRedType()).thenReturn("Human");
        when(mockLeftMenu.getBlueType()).thenReturn("Computer");

        doThrow(new IllegalArgumentException("Board size must be between 5 and 11."))
                .when(mockGameUtils)
                .startNewGame(anyInt(), anyString(), anyString(), anyString());

        // Mock Alert creation safely
        try (MockedConstruction<Alert> mocked = mockConstruction(Alert.class,
                (mock, context) -> when(mock.showAndWait()).thenReturn(Optional.of(ButtonType.OK)))) {

            // ✅ run inside JavaFX thread
            Platform.runLater(() -> assertDoesNotThrow(() -> gameUIInstance.startNewGame()));

            // Wait briefly for FX thread to finish
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }
    }

    @Test
    public void testStartNewGameDefaultsToBoardSize5OnInvalidInput() {
        when(mockTopMenu.getBoardSize()).thenReturn(3); // invalid size
        when(mockTopMenu.getMode()).thenReturn("General");
        when(mockRightMenu.getRedType()).thenReturn("Human");
        when(mockLeftMenu.getBlueType()).thenReturn("Human");
        when(mockGameBoardUI.createGameBoard(anyInt(), any())).thenReturn(new VBox());

        // Simulate exception thrown for invalid size
        doThrow(new IllegalArgumentException("Board size must be between 5 and 11."))
                .when(mockGameUtils)
                .startNewGame(anyInt(), anyString(), anyString(), anyString());

        try (MockedConstruction<Alert> mocked = mockConstruction(Alert.class,
                (mock, context) -> when(mock.showAndWait()).thenReturn(Optional.of(ButtonType.OK)))) {

            Platform.runLater(() -> gameUIInstance.startNewGame());

            // Wait for FX thread
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}

            // Should fall back to default size 5 and selected mode
            verify(mockGameUtils).startNewGame(5, "General", "Human", "Human" );
        }
    }


    // ---------- Utility ----------

    private Object getPrivateField(Object obj, String fieldName) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            fail("Could not access private field: " + fieldName);
            return null;
        }
    }

    private void mockStaticAlert() {
        Alert alert = mock(Alert.class);
        when(alert.showAndWait()).thenReturn(Optional.of(ButtonType.OK));
    }

}
