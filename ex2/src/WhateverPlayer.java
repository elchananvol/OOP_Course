import java.util.Random;

/**
 * this class will implement random player.
 * every turn the player will random coordinate in [1-board.size]*[1-board.size]
 * if the coordinate full we will try again until the empty coordinate will found.
 *  note: it could be very inefficiency if most coordinate full and board big.
 */
public class WhateverPlayer implements Player {
    private final Random r = new Random();
    public void playTurn(Board board, Mark mark) {
        while (true) {
            int r1 = r.nextInt(Board.SIZE);
            int r2 = r.nextInt(Board.SIZE);
            if (board.putMark(mark, r1, r2)) {
                break;
            }
        }
    }
}
