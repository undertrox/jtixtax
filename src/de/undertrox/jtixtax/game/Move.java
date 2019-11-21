package de.undertrox.jtixtax.game;

public class Move {
    public int bigRow;
    public int bigCol;
    public int smallRow;
    public int smallCol;

    public Move(int bigRow, int bigCol, int smallRow, int smallCol) {
        this.bigRow = bigRow;
        this.bigCol = bigCol;
        this.smallRow = smallRow;
        this.smallCol = smallCol;
    }

    public Move(int bigRow, int bigCol) {
        this.bigRow = bigRow;
        this.bigCol = bigCol;
        this.smallRow = -1;
        this.smallCol = -1;
    }

    public String toString() {
        return String.format("Move(%s, %s, %s, %s)", bigRow, bigCol, smallRow, smallCol);
    }
}
