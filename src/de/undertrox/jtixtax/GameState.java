package de.undertrox.jtixtax;

import java.util.Arrays;

public class GameState {
    Box[][][][] state;
    boolean[][] active;

    GameState(Box[][][][] state, boolean[][] active) {
        this.state = state;
        this.active = active;
    }

    /**
     * Returns the Box state at the corresponding Coordinates
     */
    public Box getBox(int bigRow, int bigCol, int smallRow, int smallCol) {
        return state[bigRow][bigCol][smallRow][smallCol];
    }

    /**
     * returns whether the Cell at the coordinates is currently active.
     */
    public boolean isActive(int bigRow, int bigCol) {
        return active[bigRow][bigCol];
    }
}
