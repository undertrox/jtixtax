package de.undertrox.jtixtax.ai.neuralnetwork;

import de.undertrox.jtixtax.ai.Player;
import de.undertrox.jtixtax.game.Game;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Train {
    private static Random r = new Random();

    public static void main(String[] args) {
        int maxIterations = 5000;
        int playerNum = 100;
        int rounds = 1;
        boolean read = true;
        NeuralNetworkPlayer[] players = new NeuralNetworkPlayer[playerNum];
        if (read) {
            try {
                FileInputStream readData = new FileInputStream("players.dat");
                ObjectInputStream readStream = new ObjectInputStream(readData);

                players = (NeuralNetworkPlayer[]) readStream.readObject();
                readStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i < playerNum; i++) {
                players[i]=new NeuralNetworkPlayer("Player" + i);
            }
        }
        for (int i = 0; i<maxIterations; i++) {
            long totalScore =0;
            for (int h = 0; h<rounds; h++) {
                for (int j = 0; j < playerNum; j++) {
                    NeuralNetworkPlayer p1 = players[j];
                    for (int k = j%10; k < playerNum; k+=10) {
                        p1.wrongMoves =0;
                        Player p2 = players[k];
                        Game g = new Game(p1, p2);
                        while (!g.hasEnded()) {
                            g.playOneTurn();
                        }
                        if (g.getWinner() == p1) {
                            players[j].score+=10;
                            players[k].score-=10;
                        } else if (g.getWinner() == p2) {
                            players[j].score-=10;
                            players[k].score+=10;
                        }
                        System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b" +
                                             "\b\b\b\b\b\b\b");
                        System.out.printf("%s/%s [%s/%s] Player%s: %s wrong moves",
                                          i, maxIterations,
                                          h*playerNum*playerNum+j * playerNum + k,
                                          rounds * playerNum * playerNum, j, p1.wrongMoves
                        );
                        totalScore += p1.score;
                    }
                }
            }

            NeuralNetworkPlayer[] newPlayers = new NeuralNetworkPlayer[playerNum];
            Arrays.sort(players);
            System.out.println();
            System.out.printf("Average score: %s; Best score: %s\n", 1.0*totalScore/playerNum*rounds, players[0].score);
            newPlayers[0] = players[0];
            for (int j = 1; j < playerNum; j++) {
                int index =(int) Math.abs(r.nextGaussian()*0.2)*(playerNum-1);
                if (index >= playerNum) index =0;
                NeuralNetworkPlayer p = players[index];
                newPlayers[j] = new NeuralNetworkPlayer("Player"+j);
                newPlayers[j].setNeuralNetwork(r.nextDouble()<0.1?
                                               p.getNeuralNetwork().offspring()
                                                                 : p.getNeuralNetwork());
            }
            players = newPlayers;
            try{
                FileOutputStream writeData = new FileOutputStream("players.dat");
                ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

                writeStream.writeObject(players);
                writeStream.flush();
                writeStream.close();

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
