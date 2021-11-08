//import javax.swing.Renderer;

/**
 * this class will do round of game. so given two players and GUI (renderer),
 * we have method (that can be used many times) to create a board and run one round of game.
 */
public class Game {
    private final Player playerX;
    private final Player playerO;
    private final Renderer renderer;

    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;

    }

    /**
     * method to run game. its play one round all the game until end.
     * @return the mark winner of the game.
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





}
