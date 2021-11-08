/**
 *  a factory for create new player show.
 */


public class PlayerFactory {
    static String HUMAN_PLAYER = "human";
    static String RANDOM_PLAYER = "whatever";
    static String CLEVER_PLAYER = "clever";
    static String SUPER_SMART_PLAYER = "snartypamts";

    PlayerFactory() {}
    Player buildPlayer(String str) {
        if (str.equals(HUMAN_PLAYER)) {
            return new HumanPlayer();
        }
        if (str.equals(RANDOM_PLAYER)) {
            return new WhateverPlayer();
        }
        if (str.equals(CLEVER_PLAYER)) {
            return new CleverPlayer();
        }
        if (str.equals(SUPER_SMART_PLAYER)) {
            return new SnartypamtsPlayer();
        }
        return null;
    }

}
