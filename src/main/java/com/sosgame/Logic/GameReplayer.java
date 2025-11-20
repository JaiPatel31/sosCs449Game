package com.sosgame.Logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class GameReplayer {

    private Queue<ReplayMove> moves = new LinkedList<>();
    private String mode;
    private int boardSize;

    // Load the CSV replay file
    public void load(String fileName) {
        moves.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            // First line: mode=General,size=7
            String meta = br.readLine();
            String[] parts = meta.split(",");
            mode = parts[0].split("=")[1];
            boardSize = Integer.parseInt(parts[1].split("=")[1]);

            // Load moves
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                int row = Integer.parseInt(p[0]);
                int col = Integer.parseInt(p[1]);
                char letter = p[2].charAt(0);
                String player = p[3];

                moves.add(new ReplayMove(row, col, letter, player));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Returns true if there are still moves left
    public boolean hasNext() {
        return !moves.isEmpty();
    }

    // Pops the next move for ReplayComputerPlayer.chooseMove()
    public ReplayMove next() {
        return moves.poll();
    }

    // Peek the next letter without removing the move
    public char peekLetter() {
        ReplayMove next = moves.peek();
        return next != null ? next.letter : 'S';
    }

    // Metadata getters
    public int getBoardSize() {
        return boardSize;
    }

    public String getMode() {
        return mode;
    }
}

