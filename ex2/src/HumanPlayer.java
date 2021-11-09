import java.util.Scanner;
/**
the class imply functionality for human player by giving a method that handle
 with input and parse it to coordinate on board
*/
public class HumanPlayer implements Player{
    final String WRONG_COORDINATE_MSG = "Invalid coordinates, type again: ";
    HumanPlayer() {}

    /**
     * this method will continue until that we get from user to number
     * between 0-1 without separation. then we put it on the board if the place empty,
     * else again ask for place from user
     *
     * @param board this is the bord that we play on
     * @param mark  the mark that player want to put on board
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int choose;
        Scanner sc = new Scanner(System.in);
        System.out.print("Player " + mark + ", type coordinates: ");

        for (; ; ) {
            if (!sc.hasNextInt()) {
                System.out.println(WRONG_COORDINATE_MSG);
                sc.next();
                continue;
            }
            choose = sc.nextInt();
            int row = choose / 10 - 1;
            int col = choose % 10 - 1;
            if (choose <= 0 || row > Board.SIZE || col > Board.SIZE) {
                System.out.print(WRONG_COORDINATE_MSG);
                continue;
            }

            if (board.putMark(mark, row, col)) {
                break;
            }
            System.out.print(WRONG_COORDINATE_MSG);
        }


    }

}
