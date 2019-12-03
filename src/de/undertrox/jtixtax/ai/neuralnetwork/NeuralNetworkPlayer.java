package de.undertrox.jtixtax.ai.neuralnetwork;

import de.undertrox.jtixtax.ai.Player;
import de.undertrox.jtixtax.game.Box;
import de.undertrox.jtixtax.game.Move;
import de.undertrox.jtixtax.game.state.GameState;

import java.io.Serializable;

public class NeuralNetworkPlayer extends Player implements Comparable<NeuralNetworkPlayer> , Serializable {

    private NeuralNetwork nn;
    public int score;
    public int wrongMoves = 0;
    private static final long serialVersionUID = -8695687118110368340L;

    public NeuralNetworkPlayer() {
        super("");

    }

    public NeuralNetworkPlayer(String name) {
        super(name);
        nn = new NeuralNetwork(81, 64, 64, 128, 81);
    }

    @Override
    public Move play(GameState gameState) {
        double[] input = new double[81];

        for (int i = 0; i<81; i++) {
            Box b = gameState.getCell(i/27%3, i/9%3)
                             .getBox(i/3%3, i%3);
            input[i] = b == getColor() ?
                       1 :
                       b == getColor().getOpposite() ?
                           0 : 0.5;
        }
        double[] out = nn.input(input);
        Move m;
        do {
            double maxVal = out[0];
            int maxIndex = 0;
            for (int i = 0; i < out.length; i++) {
                if (out[i] > maxVal) {
                    maxVal = out[i];
                    maxIndex = i;
                }
            }
            m = new Move(maxIndex / 27 % 3, maxIndex / 9 % 3,
                              maxIndex / 3 % 3, maxIndex % 3
            );
            out[maxIndex] = -1;
            score--;
            wrongMoves++;
        } while (!gameState.isValidMove(m));
        return m;
    }

    public NeuralNetwork getNeuralNetwork() {
        return nn;
    }

    public void setNeuralNetwork(NeuralNetwork n) {
        nn = n;
    }

    @Override
    public int compareTo(NeuralNetworkPlayer neuralNetworkPlayer) {
        return -Integer.compare(score, neuralNetworkPlayer.score);
    }
}
