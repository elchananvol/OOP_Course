import java.util.Random;

public class WhateverPlayer implements Player {


    public void playTurn(Board board, Mark mark) {
        Random r = new Random();
        while (true) {
            int r1 = r.nextInt(Board.SIZE);
            int r2 = r.nextInt(Board.SIZE);
            if (board.putMark(mark, r1, r2)) {
                break;
            }
        }
    }
}
