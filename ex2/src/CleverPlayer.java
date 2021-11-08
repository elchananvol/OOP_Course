import java.util.Random;

public class CleverPlayer implements Player{

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
