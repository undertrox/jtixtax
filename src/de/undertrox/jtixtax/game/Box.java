package de.undertrox.jtixtax.game;

public enum Box {
    RED,
    BLUE,
    EMPTY,
    FULL;

    public Box getOpposite() {
        switch (this) {
            case RED:
                return BLUE;
            case BLUE:
                return RED;
            case EMPTY:
                return FULL;
            case FULL:
                return EMPTY;
            default:
                return null;
        }
    }
}
