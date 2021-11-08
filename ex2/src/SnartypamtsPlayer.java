import java.util.Random;




/**
 * this class will implement smart player.
 * in initialization the player will random coordinate in [1-board.size]*[1-board.size]
 * from that point, the clever will try to full all coordinate by order going right.
 */
public class SnartypamtsPlayer implements Player {
    private int row;
    private int col;
    Random r = new Random();

    SnartypamtsPlayer() {
        row = r.nextInt(Board.SIZE);
        col = r.nextInt(Board.SIZE);
    }

    /**
     * this method are responsible on the way that player response.
     * first the player try to block the other from winning, then it fills the board by column up to down
     * @param board board object to fill
     * @param mark the right sign
     */
    public void playTurn(Board board, Mark mark) {
        if (findAlmostWin(board,mark))
        {
            return;
        }
        while (true) {
            if (board.putMark(mark, row, col)) {
                row++;
                break;
            }
            row++;
            if (row > Board.SIZE) {
                row=0;
                col ++;
            }
            if (col > Board.SIZE) {
                col = 0;
            }
        }
    }

    /**
     * this method given board and two coordinate (row1,col1) (row2,col2)
     * then fill the first coordinate with the mark. if the first full then fill the second coordinate.
     * @return true if first or second was filled. false otherwise.
     */
    public Boolean putMarkInTwoPlaces(Board board, Mark mark, int row1, int col1, int row2, int col2) {
        if (board.putMark(mark, row1, col1)) {
            return true;
        }
        return board.putMark(mark, row2, col2);
    }

    /**
     * this method will implement strategy that if the opponent have almost enough streak
     * to win the smart player will block him.
     * it happens by search all over the board, if it finds WIN_STREAK - 1 marks in a row
     * it will block by mark the next. note that sometimes the opponent can still win if
     * he has place to fill mark from the second size.
     * @param board board
     * @param mark mark
     * @return true if in the search its fill the board with the mark
     */
    public boolean findAlmostWin(Board board,Mark mark){
        Mark opposite = Mark.X;
        if (mark == opposite) {
            opposite = Mark.O;
        }
        int almostWin = Board.WIN_STREAK - 1;
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                if (board.getMark(row, col) == opposite) {
                    int counter_row = 1;
                    int counter_col = 1;
                    int counter_diag = 1;
                    int counter_sec_diag = 1;
                    for (int k = 1; k + 1 < Board.WIN_STREAK; k++) {
                        if (board.getMark(row + k, col) == opposite) {
                            counter_row++;
                        }
                        if (board.getMark(row, col + k) == opposite) {
                            counter_col++;
                        }
                        if (board.getMark(row + k, col + k) == opposite) {
                            counter_diag++;
                        }
                        if (board.getMark(row + k, col - k) == opposite) {
                            counter_sec_diag++;
                        }

                        if (counter_row == Board.WIN_STREAK - 1) {
                            if (putMarkInTwoPlaces(board, mark, row + almostWin, col, row -1 , col)) {

                                return true;
                            }
                        }
                        if (counter_col == almostWin) {
                            if (putMarkInTwoPlaces(board, mark, row, col + almostWin, row, col -1 )) {

                                return true;
                            }
                        }
                        if (counter_diag == almostWin) {
                            if (putMarkInTwoPlaces(board, mark, row - 1, col - 1, row + almostWin, col + almostWin)) {
                                return true;
                            }
                        }
                        if (counter_sec_diag == almostWin) {
                            if (putMarkInTwoPlaces(board, mark, row - 1, col + 1, row + almostWin, col - almostWin)) {
                                return true;
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

}
