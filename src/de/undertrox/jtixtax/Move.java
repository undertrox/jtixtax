package de.undertrox.jtixtax;

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
}
