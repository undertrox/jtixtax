package de.undertrox.jtixtax;

import java.util.Arrays;

public class GameState {
    private CellState[][] state;
    private Game parentGame;

    GameState(CellState[][] state, Game g) {
        this.state = state;
        this.parentGame = g;
    }

    /**
     * Returns the Box state at the corresponding Coordinates
     */
    public Box getBox(int bigRow, int bigCol, int smallRow, int smallCol) {
        return getCell(bigRow, bigCol).getBox(smallRow, smallCol);
    }

    public CellState getCell(int row, int col) {
        return state[row][col];
    }

    /**
     * returns whether the Cell at the coordinates is currently active.
     */
    public boolean isActive(int bigRow, int bigCol) {
        return state[bigRow][bigCol].isActive();
    }

    /**
     * returns whether setting a Cross or Circle at the coords is possible
     */
    public boolean canSet(int bigRow, int bigCol, int smallRow, int smallCol) {
        return state[bigRow][bigCol].canSet(smallRow, smallCol);
    }

    public String draw() {
        String[] res = new String[21];
        Arrays.fill(res, "");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String[] next = state[i][j].draw();
                for (int k = 0; k < 7; k++) {
                    res[7 * i + k] += next[k];
                }
            }
        }
        return Arrays.toString(res)
                     .replace(", ", "\n")
                     .replace("[", "")
                     .replace("]", "");

    }

    /**
     * returns the Gamestate as it would be after setting a Cross or Circle at
     * the specified position without changing the actual game. returns null
     * if nothing can be done on the Specified coordinates.
     */
    public GameState nextState(int bigRow, int bigCol, int smallRow, int smallCol) {
        if (canSet(bigRow, bigCol, smallRow, smallCol)) {
            return parentGame.copy().nextState(bigRow, bigCol, smallRow, smallCol);
        }
        return null;
    }
}
