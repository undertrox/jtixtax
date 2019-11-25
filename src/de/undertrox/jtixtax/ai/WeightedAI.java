package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.Box;
import de.undertrox.jtixtax.game.Move;
import de.undertrox.jtixtax.game.state.GameState;

import java.util.Random;

public class WeightedAI extends Player {

    private Player p1;
    private Player p2;
    private int p1Weight;
    private int p2Weight;
    private Random r = new Random();

    public WeightedAI(String name, Player p1, int p1Weight, Player p2, int p2Weight) {
        super(name);
        this.p1 = p1;
        this.p2 = p2;
        this.p1Weight = p1Weight;
        this.p2Weight = p2Weight;
    }

    public void init(Box color) {
        super.init(color);
        p1.init(color);
        p2.init(color);
    }

    @Override
    public Move play(GameState game) {
        int totalWeight = p1Weight + p2Weight;
        if (r.nextInt(totalWeight) < p1Weight) {
            return p1.play(game);
        } else {
            return p2.play(game);
        }
    }
}
