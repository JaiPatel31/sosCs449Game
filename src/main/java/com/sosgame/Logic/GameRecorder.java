package com.sosgame.Logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class GameRecorder {

    private Queue<ReplayMove> moveQueue = new LinkedList<>();
    private String fileName;

    public void start(String mode, int boardSize) {
        this.fileName = "recording_" + System.currentTimeMillis() + ".csv";

        // Write metadata
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.println("mode=" + mode + ",size=" + boardSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recordMove(int row, int col, char letter, String player) {
        moveQueue.add(new ReplayMove(row, col, letter, player));
    }

    public void stop() {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            while (!moveQueue.isEmpty()) {
                ReplayMove m = moveQueue.poll();
                out.println(m.row + "," + m.col + "," + m.letter + "," + m.player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public Queue<ReplayMove> getMoveQueue() {
        return moveQueue;
    }
}

