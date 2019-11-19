package de.undertrox.tixtax.ai;

import de.undertrox.tixtax.Game;
import de.undertrox.tixtax.Player;

import java.util.Random;

public class RandomAI extends Player {

    private static Random r = new Random();
    public RandomAI(String name) {
        super(name);
    }

    @Override
    public int[] play(Game game) {
        int bigRow, bigCol, smallRow, smallCol;
        do {
            bigRow = r.nextInt(3);
            bigCol = r.nextInt(3);
            smallRow = r.nextInt(3);
            smallCol = r.nextInt(3);
        } while (!canSet(bigRow,bigCol,smallRow,smallCol));
        return new int[] {bigRow,bigCol,smallRow,smallCol};
    }
}
