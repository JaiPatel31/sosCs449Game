package com.sosgame.Logic;

import com.sosgame.Logic.GameBoard;
import com.sosgame.Logic.Player;

public class GameUtils {
    GameBoard gameBoard;
    Player PlayerRed;
    Player PlayerBlue;
    String gameMode; // "Simple" or "General"

    // Start a new game with given parameters
    void startNewGame(int boardSize, String gameMode, String player1Type, String player2Type) {
        this.gameBoard = new GameBoard(boardSize);
        this.gameMode = gameMode;
        this.PlayerRed = new Player("Red", player1Type);
        this.PlayerBlue = new Player("Blue", player2Type);
        // Red always starts first
        this.PlayerRed.setTurn(true);
        this.PlayerBlue.setTurn(false);
    }

    void MakeMove(int row, int col) {
        Player currentPlayer = PlayerRed.isTurn() ? PlayerRed : PlayerBlue;
        gameBoard.placeLetter(row, col, currentPlayer.getSelectedLetter(), currentPlayer.getColor());
        // After placing the letter, switch turns
        SwitchTurn();
    }

    void SwitchTurn() {
        if (PlayerRed.isTurn()) {
            PlayerRed.setTurn(false);
            PlayerBlue.setTurn(true);
        } else {
            PlayerRed.setTurn(true);
            PlayerBlue.setTurn(false);
        }
    }
}
