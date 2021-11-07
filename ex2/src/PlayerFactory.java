public class PlayerFactory {
    PlayerFactory() {
    }

    Player buildPlayer(String str) {
        if (str.equals("human")) {
            return new HumanPlayer();
        }


        return null;
    }


}
