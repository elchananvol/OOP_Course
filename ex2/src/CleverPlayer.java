import java.util.Random;
/**
 * this class will implement clever player.
 * in initialization the player will random coordinate in [1-board.size]*[1-board.size]
 * from that point, the clever will try to full all coordinate by order going right.
 */
public class CleverPlayer implements Player {
    private int row ;
    private int col;
    Random r = new Random();

    CleverPlayer() {
        row = r.nextInt(Board.SIZE);
        col = r.nextInt(Board.SIZE);
    }

    public void playTurn(Board board, Mark mark) {
        while (true) {
            if (board.putMark(mark, row, col)) {
                col++;
                break;
            }
            col++;
            if(col>Board.SIZE)
            {
                row++;
                col=0;
            }
            if(row>Board.SIZE)
            {
                row=0;
            }

        }
    }
}
