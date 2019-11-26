package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.Move;
import de.undertrox.jtixtax.game.state.GameState;


public class MinMaxAI extends Player{
    public MinMaxAI(String name) {
        super(name);
    }

    @Override
    public Move play(GameState gameState) {
        MinMaxNode root = new MinMaxNode(gameState, getColor());
        return root.getBestMove();
    }


}
