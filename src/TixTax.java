import de.undertrox.jtixtax.ai.*;
import de.undertrox.jtixtax.ai.neuralnetwork.NeuralNetwork;
import de.undertrox.jtixtax.ai.neuralnetwork.NeuralNetworkPlayer;
import de.undertrox.jtixtax.game.Game;

import java.io.*;
import java.util.Arrays;
import java.util.Random;


public class TixTax {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static Random r = new Random();
    public static void main(String[] args) {
        Player[] players = new Player[2];
        try {
            FileInputStream readData = new FileInputStream("players.dat");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            players = (NeuralNetworkPlayer[]) readStream.readObject();
            readStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Arrays.sort(players);
        Player p1 = players[0];
        Player p2 = new CommandLinePlayer("Test");
        //test(p1, p2, 100);

        Game g = new Game(p1, p2);
        while (!g.hasEnded()) {
            g.playOneTurn();
            //System.out.println(g.draw());
        }
        //System.out.println(g.status());
    }

    private static double getDouble(String message) {
        System.out.print(message);
        double i = 0;
        try {
            i = Double.parseDouble(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
            i = getDouble(message);
        } catch (IOException e) {
            throw new RuntimeException("cant read console");
        }
        return i;
    }

    public static void test(Player p1, Player p2, int iterations) {
        int p1won = 0;
        int p2won = 0;
        int ties = 0;
        for (int i = 0; i < iterations; i++) {
            System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
            System.out.printf("%s/%s [%s|%s|%s]",
                              i, iterations,
                              p1won, ties, p2won
            );
            Game g;
            if (Math.random() < 0.5) {
                g = new Game(p1, p2);
            } else {
                g = new Game(p2, p1);
            }
            while (!g.hasEnded()) {
                g.playOneTurn();
            }
            if (g.getWinner() == p1) {
                p1won++;
            } else if (g.getWinner() == p2) {
                p2won++;
            } else {
                ties++;
            }
        }
        System.out.println();
        System.out.printf("%s won %s times\n", p1, p1won);
        System.out.printf("%s won %s times\n", p2, p2won);
        System.out.printf("%s ties\n", ties);
        ;
    }
}
