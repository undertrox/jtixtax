package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.Box;
import de.undertrox.jtixtax.game.Move;
import de.undertrox.jtixtax.game.state.GameState;

import java.util.ArrayList;
import java.util.List;

public class MinMaxNode {
    MinMaxNode parent;
    List<MinMaxNode> children;
    GameState state;
    Box color;
    Move move;
    double score = Double.NaN;

    public MinMaxNode(GameState s, Box color) {
        state = s;
        this.color = color;
    }

    private MinMaxNode(GameState s, Box color, Move m) {
        this(s, color);
        this.move = m;
    }

    public List<MinMaxNode> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
            for (Move m : state.getValidMoves()){
                MinMaxNode child = new MinMaxNode(state.nextState(m), color, m);
                child.parent = this;
                children.add(child);
            }
        }
        return children;
    }

    public double getScore(int level) {
        if (Double.isNaN(score)) {
            if (level == 0) {
                score = getScore();
            } else {
                score = 0;
            }
        }
        return score;
    }

    public Move getBestMove() {
        Move bestMove = getChildren().get(0).move;
        double bestScore = getChildren().get(0).getScore();
        for (MinMaxNode child : getChildren()) {
            if (child.getScore() > bestScore) {
                bestScore = child.getScore(0);
                bestMove = child.move;
            }
        }
        return bestMove;
    }

    private double getScore() {
        int score = 0;
        Box winner = state.getWinner();
        if (winner == color) {
            return 1000;
        } else if (winner == color.getOpposite()) {
            return -1000;
        }
        List<Move> ownCells = new ArrayList<>();
        List<Move> opponentCells = new ArrayList<>();
        List<Move> fullCells = new ArrayList<>();
        List<Move> emptyCells = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            for (int j = 0; j <3; j++) {
                if (state.getCell(i,j).getTotalState() == color) {
                    ownCells.add(new Move(i,j));
                    score += 100;
                } else if (state.getCell(i,j).getTotalState() == color.getOpposite()) {
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
