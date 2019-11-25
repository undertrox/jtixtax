package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.Box;
import de.undertrox.jtixtax.game.Move;
import de.undertrox.jtixtax.game.state.GameState;

import java.util.ArrayList;
import java.util.List;

public class MinMaxAI extends Player{
    public MinMaxAI(String name) {
        super(name);
    }

    @Override
    public Move play(GameState gameState) {
        return null;
    }

    public int getScore(GameState state) {
        int score = 0;
        List<Move> ownCells = new ArrayList<>();
        List<Move> opponentCells = new ArrayList<>();
        List<Move> fullCells = new ArrayList<>();
        List<Move> emptyCels = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            for (int j = 0; j <3; j++) {
                if (state.getCell(i,j).getTotalState() == getColor()) {
                    ownCells.add(new Move(i,j));
                } else if (state.getCell(i,j).getTotalState() == getColor().getOpposite()) {
                    opponentCells.add(new Move(i,j));
                } else if (state.getCell(i,j).getTotalState() == Box.FULL){
                    fullCells.add(new Move(i,j));
                } else {
                    emptyCels.add(new Move(i,j));
                }
            }
        }

        return score;
    }
}
