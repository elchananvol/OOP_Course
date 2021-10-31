public class Game {
    public static void main(String[] args) {
        Renderer printer = new Renderer();
        Board board = new Board();
        board.putMark(Mark.X,2,2);
        printer.renderBoard(board);
    }
}
