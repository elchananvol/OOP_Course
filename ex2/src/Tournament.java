public class Tournament {
    public static void main(String[] args) {
        Renderer r = new ConsoleRenderer();
        Player pa = new HumanPlayer();
        Player pb = new HumanPlayer();
        Board b = new Board();


//        for (int i = 0; i < 3; i++) {
//            b.putMark(Mark.X, 3, 1 + i);
//        }
//        Mark winner = b.getWinner();
//        r.renderBoard(b);

        Game game = new Game(pa,pb,r);
        Mark winner = game.run();

        if (winner == Mark.X) {
            System.out.print("X win");
        }
        if (winner == Mark.O) {
            System.out.print("O win");
        }

    }

    }
