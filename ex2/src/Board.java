import java.util.Arrays;

public class Board {
    public static final int SIZE = 4;
    public static final int WIN_STREAK = 3;
    private Mark[][] board= new Mark[SIZE][SIZE];
    private boolean game_ended = false;
    private Mark winner = Mark.BLANK;
    Board() {
        for(int i=0;i<SIZE;i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Mark.BLANK;
            }
        }
    }
    public Mark getMark(int i, int j){
        if (i>SIZE || i<1 || j>SIZE || j<1)
        {
            return Mark.BLANK;
        }
        return board[i][j];
    }
    boolean putMark(Mark mark,int i, int j){
        if ((i<=SIZE && i>0) || (j<=SIZE && j>0))
        {
            if (board[i][j] ==Mark.BLANK){
                board[i][j] =mark;
                return true;
            }
        }
        return false;
    }
    public boolean gameEnded(){
        if(game_ended){
            return true;
        }
        Mark[] temp ={Mark.O,Mark.X};
        for (Mark mark : temp){
            for (int row=0;row<SIZE;row++)
            {
                for (int col=0;col<SIZE;col++)
                {
                    int counter_row =0;
                    int counter_col =0;
                    int counter_diag =0;
                    for(int k=0;k<WIN_STREAK;k++){
                        if (getMark(row+k,col)== mark)
                        {
                            counter_row++;
                        }
                        if (getMark(row,col+k)== mark)
                        {
                            counter_col++;
                        }
                        if (getMark(row+k,col+k)== mark)
                        {
                            counter_diag++;
                        }

                    }
                    if (counter_row ==WIN_STREAK || counter_col ==WIN_STREAK||counter_diag ==WIN_STREAK)
                    {
                       winner = mark;
                       return true;
                    }

                }

            }

        }
        for (int row=0;row<SIZE;row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == Mark.BLANK){
                    return false;
                }

            }
        }

        return true;
    }

    public Mark getWinner() {

        return winner;
    }

}
