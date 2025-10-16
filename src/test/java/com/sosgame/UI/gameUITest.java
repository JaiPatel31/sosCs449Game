package com.sosgame.UI;

import com.sosgame.Logic.GameUtils;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class gameUITest {

    private gameUI gameUIInstance;
    private TopMenu mockTopMenu;
    private LeftMenu mockLeftMenu;
    private RightMenu mockRightMenu;
    private GameBoardUI mockGameBoardUI;
    private GameUtils mockGameUtils;

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

    @Test
    public void testStartNewGameSuccessfullyCallsGameUtilsAndSetsCenter() {
        when(mockTopMenu.getBoardSize()).thenReturn(5);
        when(mockTopMenu.getMode()).thenReturn("Simple");
        when(mockRightMenu.getRedType()).thenReturn("Human");
        when(mockLeftMenu.getBlueType()).thenReturn("Computer");
        when(mockGameBoardUI.createGameBoard(anyInt(), any())).thenReturn(new VBox());

        gameUIInstance.startNewGame();

        verify(mockGameUtils, times(1)).startNewGame(5, "Human", "Computer", "Simple");
        assertNotNull(((BorderPane) getPrivateField(gameUIInstance, "root")).getCenter());
    }

    // ---------- startNewGame() Failure Path ----------

    @Test
    public void testStartNewGameShowsAlertOnInvalidSize() {
        when(mockTopMenu.getBoardSize()).thenReturn(3); // invalid (below 5)
        when(mockTopMenu.getMode()).thenReturn("Simple");
        when(mockRightMenu.getRedType()).thenReturn("Human");
        when(mockLeftMenu.getBlueType()).thenReturn("Computer");

        doThrow(new IllegalArgumentException("Board size must be between 5 and 11."))
                .when(mockGameUtils).startNewGame(anyInt(), anyString(), anyString(), anyString());

        // Override Alert.showAndWait() with a stub to avoid launching UI
        mockStaticAlert();

        assertDoesNotThrow(() -> gameUIInstance.startNewGame());
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
        // Override Alert.showAndWait() statically for headless testing
        Alert alert = mock(Alert.class);
        doNothing().when(alert).showAndWait();
    }
}
