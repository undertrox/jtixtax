package de.undertrox.tixtax;

import java.util.List;

public abstract class Player {
    private Game parentGame;
    private Box color;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    /**
     * initialisiert den Spieler auf dem
     * Spielfeld game mit der Farbe color. wird automatisch vom Spiel
     * aufgerufen.
     */
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
     * gibt ein Array von zweielementigen Arrays zurueck, die jeweils die
     * Koordinaten des Feldes repraesentieren. Bsp:
     * getActiveFields() = [[0,0], [2,1]]
     * das Erste Element ist die Reihe, das zweite die Spalte
     */
    protected int[][] getActiveFields() {
        List<TicTacToe> activeFields = parentGame.getActiveFields();
        int[][] res = new int[activeFields.size()][2];
        for (int i = 0; i < res.length; i++) {
            res[i] = new int[]{activeFields.get(i).getRow(), activeFields.get(i).getCol()};
        }
        return res;
    }

    /**
     * Gibt eine komplette Repraesentation des Spielfeldes zurueck
     * format: getBoard()[bigRow][bigCol][smallRow][smallCol]
     */
    protected Box[][][][] getBoard() {
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
     * This method is called once each turn, returns an Array of the form
     * [bigRow, bigCol, smallRow, smallCol] which indicates where the player
     * will set their Cross or Circle
     */
    public abstract int[] play(Game game);


    public Box getColor() {
        return color;
    }

    /**
     * gibt zurueck, ob der Spieler an der entsprechenden Position ein Kreuz oder
     * Kreis setzen kann
     */
    protected boolean canSet(int bigRow, int bigCol, int smallRow, int smallCol) {
        return parentGame.isValidMove(bigRow, bigCol, smallRow, smallCol);
    }
}
