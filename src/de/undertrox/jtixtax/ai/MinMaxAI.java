package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.Box;
import de.undertrox.jtixtax.game.Game;
import de.undertrox.jtixtax.game.Move;
import de.undertrox.jtixtax.game.state.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinMaxAI extends Player{
    public MinMaxAI(String name) {
        super(name);
    }

    @Override
    public Move play(GameState gameState) {
        int depth = 1;
        List<List<GameState>> states = new ArrayList<>();
        for (int i = 0; i<depth; i++) {
            states.add(new ArrayList<>());
        }
        for (Move m : gameState.getValidMoves()) {
            states.get(0).add(gameState.nextState(m));
        }
        for (int i = 1; i<depth; i++) {
            for (Move m : states.get(i-1).get(i).getValidMoves()) {
                states.get(i).add(gameState.nextState(m));
            }
        }
        List<Integer> scores = new ArrayList<>();
        for (GameState s : states.get(0)) {
            scores.add(getScore(s));
        }
        int maxIndex =-1;
        int maxVal = -1000;
        for (int i = 0; i<scores.size(); i++) {
            if (maxVal < scores.get(i)) {
                maxIndex = i;
                maxVal = scores.get(i);
            }
        }
        System.out.println(gameState.getValidMoves());
        System.out.println(scores);
        return gameState.getValidMoves().get(maxIndex);
    }

    public int getScore(GameState state) {
        int score = 0;
        Box winner = state.getWinner();
        if (winner == getColor()) {
            return 1000;
        } else if (winner == getColor().getOpposite()) {
            return -1000;
        }
        List<Move> ownCells = new ArrayList<>();
        List<Move> opponentCells = new ArrayList<>();
        List<Move> fullCells = new ArrayList<>();
        List<Move> emptyCells = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            for (int j = 0; j <3; j++) {
                if (state.getCell(i,j).getTotalState() == getColor()) {
                    ownCells.add(new Move(i,j));
                    score += 100;
                } else if (state.getCell(i,j).getTotalState() == getColor().getOpposite()) {
                    opponentCells.add(new Move(i,j));
                    score -= 100;
                } else if (state.getCell(i,j).getTotalState() == Box.FULL){
                    fullCells.add(new Move(i,j));
                    score -= 20;
                } else {
                    emptyCells.add(new Move(i,j));
                }
            }
        }

        return score;
    }
}
