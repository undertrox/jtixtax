package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.Game;
import de.undertrox.jtixtax.GameState;
import de.undertrox.jtixtax.Move;
import de.undertrox.jtixtax.Player;

import java.util.Random;

public class RandomAI extends Player {

    private static Random r = new Random();

    public RandomAI(String name) {
        super(name);
    }

    @Override
    public Move play(GameState game) {
        int bigRow, bigCol, smallRow, smallCol;
        do {
            bigRow = r.nextInt(3);
            bigCol = r.nextInt(3);
            smallRow = r.nextInt(3);
            smallCol = r.nextInt(3);
        } while (!game.isValidMove(bigRow, bigCol, smallRow, smallCol));
        return new Move(bigRow, bigCol, smallRow, smallCol);
    }
}
