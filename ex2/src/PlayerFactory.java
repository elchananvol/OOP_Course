public class PlayerFactory {
    PlayerFactory() {}
    Player buildPlayer(String str) {
        if (str.equals("human")) {
            return new HumanPlayer();
        }
        if (str.equals("whatever")) {
            return new WhateverPlayer();
        }
        if (str.equals("clever")) {
            return new CleverPlayer();
        }
        if (str.equals("snartypamts")) {
            return new SnartypamtsPlayer();
        }
        return null;
    }

}
