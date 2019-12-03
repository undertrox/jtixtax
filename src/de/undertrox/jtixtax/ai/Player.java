package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.state.GameState;
import de.undertrox.jtixtax.game.Box;
import de.undertrox.jtixtax.game.Move;

public abstract class Player {
    private Box color;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public Player() {
        this("");
    }

    /**
     * initialisiert den Spieler auf dem
     * Spielfeld game mit der Farbe color. wird automatisch vom Spiel
     * aufgerufen.
     */
    public void init(Box color) {
        this.color = color;
    }

    public String toString() {
        return name;
    }

    /**
     * This method is called once each turn, returns an Array of the form
     * [bigRow, bigCol, smallRow, smallCol] which indicates where the player
     * will set their Cross or Circle
     */
    public abstract Move play(GameState gameState);


    public Box getColor() {
        return color;
    }

}
