package de.undertrox.jtixtax.ai.neuralnetwork;

import de.undertrox.jtixtax.ai.Player;
import de.undertrox.jtixtax.game.Move;
import de.undertrox.jtixtax.game.state.GameState;

public class NeuralNetworkPlayer extends Player {

    private NeuralNetwork nn;

    public NeuralNetworkPlayer(String name) {
        super(name);
        nn = new NeuralNetwork(81, 20, 81);
    }

    @Override
    public Move play(GameState gameState) {
        double[] input = new double[81];
        for (int i = 0; i<81; i++) {
            input[i] = gameState.getCell(i/27%3, i/9%3)
                                .getBox(i/3%3, i%3)
                           == getColor() ? 1 : 0;
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
        } while (!gameState.isValidMove(m));
        return m;
    }
}
