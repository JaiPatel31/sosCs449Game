package com.sosgame.Logic;

// Represents a player in the SOS game
public class Player {
    int score; // Player's score
    String type; // "Human" or "Computer"
    String color; // "Red" or "Blue"
    char selectedLetter; // 'S' or 'O'
    boolean isTurn; // True if it's this player's turn
    boolean isWinner; // True if this player has won

    // Constructor to create a new player
    public Player(String color, String type) {
        this.color = color;
        this.type = type;
        this.score = 0;
        this.selectedLetter = 'S'; // Default letter
        this.isTurn = false;
        this.isWinner = false;
    }

    // Copy constructor
    public Player(Player player) {
        this.color = player.color;
        this.type = player.type;
        this.score = player.score;
        this.selectedLetter = player.selectedLetter;
        this.isTurn = player.isTurn;
        this.isWinner = player.isWinner;
    }
    //Reset Player Score
    public void resetScore() {
        this.score = 0;
    }
    // Get the player's score
    public int getScore() {
        return score;
    }

    // Increase score by 1
    public void incrementScore() {
        this.score++;
    }

    // Increase score by a given number
    public void incrementScoreByNumber(int add) {
        this.score += add;
    }

    // Get the player's type
    public String getType() {
        return type;
    }

    // Get the player's color
    public String getColor() {
        return color;
    }

    // Get the selected letter
    public char getSelectedLetter() {
        return selectedLetter;
    }

    // Set the selected letter ('S' or 'O')
    public void setSelectedLetter(char selectedLetter) {
        if (selectedLetter == 'S' || selectedLetter == 'O') {
            this.selectedLetter = selectedLetter;
        } else {
            throw new IllegalArgumentException("Invalid letter. Only 'S' or 'O' are allowed.");
        }
    }

    // Check if it's this player's turn
    public boolean isTurn() {
        return isTurn;
    }

    // Set whether it's this player's turn
    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    // Check if this player is the winner
    public boolean isWinner() {
        return isWinner;
    }

    // Set whether this player is the winner
    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

}
