package de.undertrox.jtixtax.ai.neuralnetwork;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class NeuralNetwork {
    private Random r;
    private int layers;
    private int[] sizes;
    private double[][] biases;
    private double[][][] weights;
    private double[][] neurons;
    private int currentLayer;

    public NeuralNetwork(int... sizes) {
        r = new Random();
        this.sizes = sizes;
        layers = sizes.length;
        initBiases();
        initWeights();
        initNeurons();
    }

    public static NeuralNetwork readFrom(String filePath) {
        try{
            FileInputStream readData = new FileInputStream("peopledata.ser");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            NeuralNetwork nn = (NeuralNetwork) readStream.readObject();
            readStream.close();
            return nn;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initNeurons() {
        neurons = new double[sizes.length][];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new double[sizes[i]];
            Arrays.fill(neurons[i], 0);
        }
    }

    private void initBiases() {
        biases = new double[sizes.length - 1][];
        for (int i = 0; i < biases.length; i++) {
            biases[i] = new double[sizes[i + 1]];
            for (int j = 0; j < biases[i].length; j++) {
                biases[i][j] = r.nextGaussian();
            }
        }
    }

    private void initWeights() {
        weights = new double[sizes.length - 1][][];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = new double[sizes[i + 1]][];
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = new double[sizes[i]];
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = r.nextGaussian();
                }
            }
        }
    }

    public double[] input(double... input) {
        if (input.length != sizes[0]) {
            throw new IllegalArgumentException("Input does not match Input Schema");
        }
        neurons[0] = input;
        for (double x : input) {
            if (x < 0 || x > 1) {
                neurons[0] = sigmoid(neurons[0]);
                break;
            }
            currentLayer = 1;
            while (currentLayer < layers) {
                nextLayer();
            }
        }
        return neurons[layers - 1];
    }

    private double[] sigmoid(double... x) {
        double[] r = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            r[i] = sigmoid(x[i]);
        }
        return r;
    }

    private void nextLayer() {
        int lastLayer = currentLayer - 1;
        for (int i = 0; i < neurons[currentLayer].length; i++) {
            neurons[currentLayer][i] = 0;
            for (int j = 0; j < weights[lastLayer][i].length; j++) {
                neurons[currentLayer][i] += neurons[lastLayer][j] * weights[lastLayer][i][j];
            }
            neurons[currentLayer][i] += biases[lastLayer][i];
            neurons[currentLayer][i] = sigmoid(neurons[currentLayer][i]);
        }
        currentLayer++;
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public void writeTo(String path) {
        try{
            FileOutputStream writeData = new FileOutputStream(path);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(this);
            writeStream.flush();
            writeStream.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
