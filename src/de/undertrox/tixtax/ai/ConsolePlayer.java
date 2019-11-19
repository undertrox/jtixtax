package de.undertrox.tixtax.ai;

import de.undertrox.tixtax.Game;
import de.undertrox.tixtax.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsolePlayer extends Player {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ConsolePlayer(String name) {
        super(name);
    }

    @Override
    public int[] play(Game g) {
        System.out.println(g.draw());
        int[][] active = getActiveFields();
        int bigRow, bigCol;
        if (active.length > 1) {
            bigRow = getInt("Enter BigRow (0-2): ");
            bigCol = getInt("Enter BigCol (0-2): ");
        } else {
            bigRow = active[0][0];
            bigCol = active[0][1];
        }
        int smallRow = getInt("Enter SmallRow (0-2): ");
        int smallCol = getInt("Enter SmallCol (0-2): ");
        if (!canSet(bigRow, bigCol, smallRow, smallCol)) {
            System.out.println("Invalid Move, please try again.");
            return play(g);
        }
        return new int[]{bigRow, bigCol, smallRow, smallCol};
    }

    private int getInt(String message) {
        System.out.print(message);
        int i = 0;
        try {
            i = Integer.parseInt(br.readLine());
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid Format!");
            i = getInt(message);
        } catch (IOException e) {
            throw new RuntimeException("cant read console");
        }
        return i;
    }
}
