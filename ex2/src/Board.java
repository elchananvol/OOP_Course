import java.util.Arrays;

public class Board {
    public static final int SIZE = 4;
    public static final int WIN_STREAK = 3;
    private Mark[][] board = new Mark[SIZE][SIZE];
    private boolean game_ended = false;
    private Mark winner = Mark.BLANK;
    int counter_hold_places = 0;

    /**
     * constructor. fill the board with empty sign.
     */
    Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * getter method to get board[i,j]
     *
     * @param i i coordinate
     * @param j j coordinate
     * @return the mark in board[i,j] if it filled and i,j legal, else mark.blank will return
     */

    public Mark getMark(int i, int j) {
        if (i >= SIZE || i < 0 || j >= SIZE || j < 0) {
            return Mark.BLANK;
        }
        return board[i][j];
    }

    /**
     * if there is empty place at board[i,j] for mark, this method will put it in the matrix
     *
     * @param mark mark of player to put
     * @param i    i coordinate
     * @param j    j coordinate
     * @return false if board[i,j] out of range or if cell is filled. true upon successes
     */
    public boolean putMark(Mark mark, int i, int j) {
        if (i < SIZE && i >= 0 && j < SIZE && j >= 0) {
            if (board[i][j] == Mark.BLANK) {
                board[i][j] = mark;
                counter_hold_places++;
                return true;
            }
        }
        return false;
    }

    /**
     * check if the game and. go through all cells of the mat and check if the game end.
     * assumption: mark.blank is empty cell and the marks of players is {Mark.O, Mark.X}
     *
     * @return true if there is winner or that all cells are full.
     */
    public boolean gameEnded() {
        if (game_ended) {
            return true;
        }
        Mark[] temp = {Mark.O, Mark.X};
        for (Mark mark : temp) {
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    int counter_row = 0;
                    int counter_col = 0;
                    int counter_diag = 0;
                    int counter_sec_diag = 0;
                    for (int k = 0; k < WIN_STREAK; k++) {
                        if (getMark(row + k, col) == mark) {
                            counter_row++;
                        }
                        if (getMark(row, col + k) == mark) {
                            counter_col++;
                        }
                        if (getMark(row + k, col + k) == mark) {
                            counter_diag++;
                        }
                        if (getMark(row + k, col - k) == mark) {
                            counter_sec_diag++;
                        }

                    }
                    if (counter_row == WIN_STREAK || counter_col == WIN_STREAK ||
                            counter_diag == WIN_STREAK || counter_sec_diag == WIN_STREAK) {
                        winner = mark;
                        return true;
                    }

                }

            }

        }
        return counter_hold_places >= SIZE * SIZE;
    }

    /**
     * this method will return the winner in the game.
     *
     * @return mark of the winner
     */

    public Mark getWinner() {
        gameEnded();

        return winner;
    }

}
