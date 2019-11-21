package de.undertrox.jtixtax;

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
    /*public boolean isActive() {
        return this == parentGame.getCurrentPlayer();
    }*/

    /**
     * This method is called once each turn, returns an Array of the form
     * [bigRow, bigCol, smallRow, smallCol] which indicates where the player
     * will set their Cross or Circle
     */
    public abstract Move play(Game game);


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
