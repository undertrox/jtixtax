package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.Game;
import de.undertrox.jtixtax.GameState;
import de.undertrox.jtixtax.Move;
import de.undertrox.jtixtax.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommandLinePlayer extends Player {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public CommandLinePlayer(String name) {
        super(name);
    }

    @Override
    public Move play(GameState g) {
        System.out.println(g.draw());
        List<Move> active = g.getActiveCellCoords();
        int bigRow, bigCol;
        if (active.size() > 1) {
            bigRow = getInt("Enter BigRow (0-2): ");
            bigCol = getInt("Enter BigCol (0-2): ");
        } else {
            bigRow = active.get(0).bigRow;
            bigCol = active.get(0).bigCol;
        }
        int smallRow = getInt("Enter SmallRow (0-2): ");
        int smallCol = getInt("Enter SmallCol (0-2): ");
        if (!g.isValidMove(bigRow, bigCol, smallRow, smallCol)) {
            System.out.println("Invalid Move, please try again.");
            return play(g);
        }

        return new Move(bigRow, bigCol, smallRow, smallCol);
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
