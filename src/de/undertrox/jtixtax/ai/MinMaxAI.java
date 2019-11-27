package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.Move;
import de.undertrox.jtixtax.game.state.GameState;

import java.util.List;


public class MinMaxAI extends Player{
    private int depth;
    public MinMaxAI(String name, int depth) {
        super(name);
        this.depth = depth;
    }

    @Override
    public Move play(GameState gameState) {
        MinMaxNode root = new MinMaxNode(gameState, getColor());
        List<Move> valid = gameState.getValidMoves();
        if (valid.size() == 81) {
            return valid.get((int) (Math.random()*valid.size()));
        }
        return root.getBestMove(depth);
    }


}
