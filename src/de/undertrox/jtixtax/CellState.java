package de.undertrox.jtixtax;

public class CellState {
    Box[][] boxes;
    Box totalState;
    boolean isActive;

    public CellState() {
        boxes = new Box[3][3];
        totalState = Box.EMPTY;
        isActive = false;
    }

    public CellState(Box[][] boxes, Box totalState, boolean isActive) {
        this.boxes = boxes;
        this.totalState = totalState;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public Box getBox(int row, int col) {
        return boxes[row][col];
    }

    public Box getTotalState() {
        return totalState;
    }
}
