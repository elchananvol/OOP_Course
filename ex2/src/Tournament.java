public class Tournament {
    private static String ERROR = "Usage: java Tournament [round count] [render target: console/none] [player1: human/clever/whatever/snartypamts] [player2: human/clever/whatever/snartypamts]";


    private int rounds;
    private Renderer renderer;
    private Player player1;
    private Player player2;

    Tournament(int rounds, Renderer renderer, Player[] players) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = players[0];
        this.player2 = players[1];

    }

    public void playTournament() {
        Game type1 = new Game(player1, player2, renderer);
        Game type2 = new Game(player2, player1, renderer);
        Game[] types = {type1, type2};
        int[] counters = {0, 0, 0};
        for (int i = 0; i < rounds; i++) {
            Mark mark = types[i % 2].run();
            if (mark == Mark.X) {
                counters[i % 2]++;
            }
            if (mark == Mark.O) {
                counters[(i+1) % 2]++;
            }
        }
        counters[2] = rounds - counters[1] - counters[0];
        System.out.printf("=== player 1: %d | player 2: %d | Draws: %d === ===", counters[0], counters[1], counters[2]);
        return ;
    }


    public static void main(String[] args) {
        int count;
        if (args.length < 4) {
            System.out.println(ERROR);
            return;
        }
        if (args[0] != null && args[0].matches("[0-9.]+")) {
            count = Integer.parseInt(args[0]);
        }
        else{
            System.out.println(ERROR);
            return;

        }


        RendererFactory r = new RendererFactory();
        PlayerFactory p = new PlayerFactory();
        Renderer re = r.buildRenderer(args[1]);
        Player[] players = {p.buildPlayer(args[2]),p.buildPlayer(args[3])};

        if (count <= 0 || re == null || players[0] == null || players[1] == null) {
            System.out.println(ERROR);
            return;
        }

        Tournament t = new Tournament(count, re, players);
        t.playTournament();



//        for (int i = 0; i < 3; i++) {
//            b.putMark(Mark.X, 3, 1 + i);
//        }
//        Mark winner = b.getWinner();
//        r.renderBoard(b);

//        Game game = new Game(pa,pb,r);
//        Mark winner = game.run();
//
//        if (winner == Mark.X) {
//            System.out.print("X win");
//        }
//        if (winner == Mark.O) {
//            System.out.print("O win");
//        }

    }

}
