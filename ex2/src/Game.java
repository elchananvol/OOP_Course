//import javax.swing.Renderer;

public class Game {
    private Player playerX;
    private Player playerO;
    private Renderer renderer;

    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;

    }

    /**
     * main method to run the game. it play the game until its end.
     *
     * @return the winner of the game.
     */
    public Mark run() {
        Board board = new Board();
        int i = 0;
        Player[] players = {playerX, playerO};
        Mark[] marks = {Mark.X, Mark.O};
        while (!board.gameEnded()) {
            players[i].playTurn(board, marks[i]);
            renderer.renderBoard(board);
            i = 1 - i;
        }
        return board.getWinner();

    }


    public static void main(String[] args) {
        Renderer r = new Renderer();
        Player pa = new Player();
        Player pb = new Player();
        Board b = new Board();


        for (int i = 0; i < 3; i++) {
            b.putMark(Mark.X, 3, 1 + i);
        }
        Mark winner = b.getWinner();
        r.renderBoard(b);

//        Game game = new Game(pa,pb,r);
//        Mark winner = game.run();

        if (winner == Mark.X) {
            System.out.print("X win");
        }
        if (winner == Mark.O) {
            System.out.print("O win");
        }


    }


}
