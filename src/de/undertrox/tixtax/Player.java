package de.undertrox.tixtax;

import java.util.Arrays;
import java.util.List;

public class Player {
    private Game parentGame;
    private Box color;
    private String name;

    public Player(String name, Box color, Game game) {
        this.name = name;
        this.init(color, game);
    }

    public Player(String name) {
        this.name = name;
    }

    public void init(Box color, Game game) {
        this.color = color;
        this.parentGame = game;
    }

    public String toString() {
        return name;
    }

    /**
     * gibt zurueck, ob der Spieler gerade am Zug ist
     */
    public boolean isActive() {
        return this == parentGame.getCurrentPlayer();
    }

    /**
     * gibt ein 3x3 boolean-Array zurueck, das das "grosse" Spielfeld repraesentiert.
     * in allen Feldern, die true sind darf gesetzt werden. Bsp:
     * getActiveFields()[row][col]
     * @return
     */
    public boolean[][] getActiveFields() {
        List<TicTacToe> activeFields = parentGame.getActiveFields();
        boolean[][] res = new boolean[3][3];
        for (boolean[] b : res) {
            Arrays.fill(b, false);
        }
        for (TicTacToe field : activeFields) {
            res[field.getRow()][field.getCol()] = true;
        }
        return res;
    }

    /**
     * Gibt eine komplette Repraesentation des Spielfeldes zurueck
     * format: getBoard()[bigRow][bigCol][smallRow][smallCol]
     * @return
     */
    public Box[][][][] getBoard() {
        TicTacToe[][] board = parentGame.getBoard();
        Box[][][][] fboard = new Box[3][3][3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                fboard[i][j] = board[i][j].toArray();
            }
        }
        return fboard;
    }

    /**
     * Setzt ein Kreuz oder Kreis an den eingegebenen Koordinaten und
     * initiiert den naechsten Zug
     * @param bigRow
     * @param bigCol
     * @param smallRow
     * @param smallCol
     */
    public void play(int bigRow, int bigCol, int smallRow, int smallCol) {
        parentGame.getBoard()[bigRow][bigCol].setBox(smallRow, smallCol, color);
        parentGame.nextTurn();
    }

    /**
     * gibt zurueck, ob der Spieler an der entsprechenden Position ein Kreuz oder
     * Kreis setzen kann
     * @param bigRow
     * @param bigCol
     * @param smallRow
     * @param smallCol
     * @return
     */
    public boolean canPlay(int bigRow, int bigCol, int smallRow, int smallCol) {
        return parentGame.isValidMove(bigRow, bigCol, smallRow, smallCol);
    }
}
