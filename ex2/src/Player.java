import java.util.Scanner;


public class Player {
    Player(){}
    public void playTurn(Board board, Mark mark){
        int choose;
        Scanner sc = new Scanner(System.in);
        System.out.printf("Enter number betwin 1-%d", board.SIZE);
//        String sc = myObj.nextLine();
        for(;;) {
            if(!sc.hasNextInt() ) {
                System.out.println("only integers!: ");
                sc.next(); // discard
                continue;
            }
            choose=sc.nextInt();
            if( choose<=0 || choose>board.SIZE)
            {
                System.out.printf("no, 1-%d: ",board.SIZE);
                continue;
            }
            if (board.putMark(mark,choose))

            break;
        }


    }

}
