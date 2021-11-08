/**
 * every type of player class should implement method playTurn
 * that determine how the player will play a turn.
 */
interface Player {
    void playTurn(Board board, Mark mark);
}
