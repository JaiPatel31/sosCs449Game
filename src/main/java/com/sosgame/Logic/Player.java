package com.sosgame.Logic;

public class Player {
    int score;
    String type; // "Human" or "Computer"
    String color; // "Red" or "Blue"
    char selectedLetter; // 'S' or 'O'
    boolean isTurn;
    boolean isWinner;

    public Player(String color, String type) {
        this.color = color;
        this.type = type;
        this.score = 0;
        this.selectedLetter = 'S'; // Default letter
        this.isTurn = false;
        this.isWinner = false;
    }

    public Player(Player player) {
        this.color = player.color;
        this.type = player.type;
        this.score = player.score;
        this.selectedLetter = player.selectedLetter;
        this.isTurn = player.isTurn;
        this.isWinner = player.isWinner;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public void incrementScoreByNumber(int add) {
        this.score += add;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public char getSelectedLetter() {
        return selectedLetter;
    }

    public void setSelectedLetter(char selectedLetter) {
        if (selectedLetter == 'S' || selectedLetter == 'O') {
            this.selectedLetter = selectedLetter;
        } else {
            throw new IllegalArgumentException("Invalid letter. Only 'S' or 'O' are allowed.");
        }
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }



}
