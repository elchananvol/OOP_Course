import java.util.Random;

public class SnartypamtsPlayer implements Player {
    private int row;
    private int col;
    Random r = new Random();

    SnartypamtsPlayer() {
        row = r.nextInt(Board.SIZE);
        col = r.nextInt(Board.SIZE);

    }

    public void playTurn(Board board, Mark mark) {
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
                            if (putMarkInTwoPlaces(board, mark, row - 1, col, row + almostWin, col)) {

                                return;
                            }
                        }
                        if (counter_col == almostWin) {
                            if (putMarkInTwoPlaces(board, mark, row, col - 1, row, col + almostWin)) {

                                return;
                            }
                        }
                        if (counter_diag == almostWin) {
                            if (putMarkInTwoPlaces(board, mark, row - 1, col - 1, row + almostWin, col + almostWin)) {
                                return;
                            }
                        }
                        if (counter_sec_diag == almostWin) {
                            if (putMarkInTwoPlaces(board, mark, row - 1, col + 1, row + almostWin, col - almostWin)) {
                                return;
                            }
                        }
                    }
                }

            }
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

    public Boolean putMarkInTwoPlaces(Board board, Mark mark, int row1, int col1, int row2, int col2) {
        if (board.putMark(mark, row1, col1)) {
            return true;
        }
        return board.putMark(mark, row2, col2);

    }

}
