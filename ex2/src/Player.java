import java.util.Scanner;


public class Player {
    Player() {
    }

    /**
     * this method will continue until that we get from user to number
     * between 0-1 without separation. then we put it on the board if the place empty,
     * else again ask for place from user
     *
     * @param board this is the bord that we play on
     * @param mark  the mark that player want to put on board
     */
    public void playTurn(Board board, Mark mark) {
        int choose;
        Scanner sc = new Scanner(System.in);
        System.out.printf("Enter number between 1-%d", Board.SIZE);
//        String sc = myObj.nextLine();
        for (; ; ) {
            if (!sc.hasNextInt()) {
                System.out.println("only integers!: ");
                sc.next(); // discard
                continue;
            }
            choose = sc.nextInt();
            int row = choose / 10 - 1;
            int col = choose % 10 - 1;
            if (choose <= 0 || row > Board.SIZE || col > Board.SIZE) {
                System.out.printf("no, only 1-%d: ", Board.SIZE);
                continue;
            }

            if (board.putMark(mark, row, col)) {
                break;
            }
            System.out.print("The border is full at this place, try again:");
        }


    }

}
