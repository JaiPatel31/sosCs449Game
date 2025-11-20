package com.sosgame.Logic;

import com.sosgame.Logic.GameReplayer;
public class ReplayComputerPlayer extends ComputerPlayer {

    private GameReplayer replayer;

    public ReplayComputerPlayer(String color, GameReplayer replayer) {
        super(color);
        this.replayer = replayer;
    }

    @Override
    public int[] chooseMove(GameBoard board) {
        // get next move from replay file
        setSelectedLetter(chooseLetter());
        ReplayMove m = replayer.next();
        if (m == null) return new int[]{-1, -1};  // no moves left

        return new int[]{m.row, m.col};
    }


    public char chooseLetter() {
        // replay comes with letters already chosen
        return replayer.peekLetter();
    }
}

