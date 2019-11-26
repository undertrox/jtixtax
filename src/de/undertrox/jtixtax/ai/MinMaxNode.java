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
    double score;// = Double.NaN;

    private MinMaxNode(GameState s, Box color, Move m) {
        this(s, color);
        this.move = m;
    }

    public MinMaxNode(GameState s, Box color) {
        state = s;
        this.color = color;
    }

    public List<MinMaxNode> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
            for (Move m : state.getValidMoves()) {
                MinMaxNode child = new MinMaxNode(state.nextState(m), color, m);
                child.parent = this;
                children.add(child);
            }
        }
        return children;
    }

    public double miniMax(int depth, double alpha, double beta) {
        if (depth == 0 || getChildren().size() == 0) {
            return getScore();
        }
        double maxScore = alpha;
        for (MinMaxNode child : getChildren()) {
            double cScore = -miniMax(depth-1, -beta, -maxScore);
            if (cScore > maxScore) {
                maxScore = cScore;
                if (maxScore >= beta) {
                    break;
                }
            }
        }
        return maxScore;
    }

    public double getScore(int level, boolean best) {
        if (getChildren().size() == 0) {
            return state.getWinner() == color ? 1000 : -1000;
        }
        if (!Double.isNaN(score)) {
            if (level == 0) {
                score = getScore();
            } else {
                if (best) {
                    score = getBestChild(level - 1).getScore(level - 1, !best);
                } else {
                    score = getWorstChild(level - 1).getScore(level - 1, !best);
                }
            }
        }
        return score;
    }

    private MinMaxNode getBestChild(int level) {
        if (getChildren().size() == 0) {
            return null;
        }
        MinMaxNode bestChild = getChildren().get(0);
        double bestScore = bestChild.getScore(level, false);
        for (MinMaxNode child : getChildren()) {
            if (child.getScore(level, false) > bestScore) {
                bestScore = child.getScore(level, false);
                bestChild = child;
            }
        }
        return bestChild;
    }

    private MinMaxNode getWorstChild(int level) {
        if (getChildren().size() == 0) {
            return null;
        }
        MinMaxNode worstChild = getChildren().get(0);
        double worstScore = worstChild.getScore(level, true);
        for (MinMaxNode child : getChildren()) {
            if (child.getScore(level, true) < worstScore) {
                worstScore = child.getScore(level, true);
                worstChild = child;
            }
        }
        return worstChild;
    }

    public Move getBestMove(int level) {

        return getBestChild(level).move;
    }

    private double getScore() {
        score = Math.random() * 10;
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state.getCell(i, j).getTotalState() == color) {
                    ownCells.add(new Move(i, j));
                    score += 100;
                } else if (state.getCell(i, j).getTotalState() == color.getOpposite()) {
                    opponentCells.add(new Move(i, j));
                    score -= 100;
                } else if (state.getCell(i, j).getTotalState() == Box.FULL) {
                    fullCells.add(new Move(i, j));
                    score -= 20;
                } else {
                    emptyCells.add(new Move(i, j));
                }
            }
        }
        return score;
    }
}
