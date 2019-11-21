package de.undertrox.jtixtax.game;

import de.undertrox.jtixtax.game.state.CellState;

import java.util.Arrays;

public class Cell {
    private Box[][] boxes;
    private Game parentGame;
    private int row, col;
    private boolean isActive;
    private Box totalState;

    /**
     * Initialisiert ein neues, leeres TTT, das zum Spiel game gehoert.
     *
     * @param game Spiel, zu dem das TTT gehoert
     */
    public Cell(Game game, int row, int col) {
        boxes = new Box[3][3];
        parentGame = game;
        for (Box[] box : boxes) {
            Arrays.fill(box, Box.EMPTY);
        }
        this.row = row;
        this.col = col;
        totalState = Box.EMPTY;
        isActive = true;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Setzt das Feld bei (row, col) auf value
     */
    public void setBox(int row, int col, Box value) {
        if (isActive) {
            if (boxes[row][col] == Box.EMPTY) {
                boxes[row][col] = value;
                checkBoxes();
            } else {
                throw new IllegalArgumentException("Tried to change a full box");
            }
        } else {
            throw new IllegalArgumentException("Tried to play in an inactive Field");
        }
    }

    public CellState getState() {
        return new CellState(deepCopy(boxes), totalState, isActive);
    }

    public Cell copy(Game newGame) {
        Cell c = new Cell(newGame, row, col);
        c.boxes = deepCopy(boxes);
        c.isActive = isActive;
        c.totalState = totalState;
        return c;
    }

    private <T> T[][] deepCopy(T[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

    private void checkBoxes() {
        boolean full = true;
        for (Box[] bs :
                boxes) {
            for (Box b : bs) {
                if (b == Box.EMPTY) {
                    full = false;
                }
            }
        }
        if (full) {
            totalState = Box.FULL;
        }
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i][0] == boxes[i][1] && boxes[i][1] == boxes[i][2]
                    && boxes[i][0] != Box.EMPTY) {
                totalState = boxes[i][0];
            } else if (boxes[0][i] == boxes[1][i] && boxes[1][i] == boxes[2][i]
                    && boxes[0][i] != Box.EMPTY) {
                totalState = boxes[0][i];
            }
        }
        if (boxes[0][0] == boxes[1][1] && boxes[1][1] == boxes[2][2]
                && boxes[0][0] != Box.EMPTY) {
            totalState = boxes[0][0];
        } else if (boxes[0][2] == boxes[1][1] && boxes[1][1] == boxes[2][0]
                && boxes[2][0] != Box.EMPTY) {
            totalState = boxes[0][2];
        }

    }

    public boolean canSetBox(int row, int col) {
        return isActive() && boxes[row][col] == Box.EMPTY;
    }

    public boolean isActive() {
        return isActive && getTotalState() == Box.EMPTY;
    }

    /**
     * gibt den Gesamtstatus des TTTs zurueck
     * Gesamtstatus = ob das ein Spieler in dem TTT Gewonnen hat
     */
    public Box getTotalState() {
        return totalState;
    }

    /**
     * aktiviert das Feld
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    public Box[][] toArray() {
        return boxes;
    }

    public String toString() {
        return Arrays.toString(draw()).replace(", ", "\n").replace("[", "");
    }

    public String[] draw() {
        return getState().draw();
    }

    private String str(Box b) {
        switch (b) {
            case RED:
                return "O";
            case BLUE:
                return "X";
            case EMPTY:
                return " ";
        }
        return "ERROR";
    }
}
