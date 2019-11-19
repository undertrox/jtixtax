import de.undertrox.tixtax.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TixTax {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        Game g = new Game("Spieler1", "Spieler2");
        //System.out.println(g.draw());
        while (!g.hasEnded()) {
            System.out.println(g.draw());
            int bigRow = getInt("Enter BigRow (0-2): ");
            int bigCol = getInt("Enter BigCol (0-2): ");
            int smallRow = getInt("Enter SmallRow (0-2): ");
            int smallCol = getInt("Enter SmallCol (0-2): ");
            g.getCurrentPlayer().play(bigRow, bigCol, smallRow, smallCol);
        }
        /*
        System.out.println(g.draw());
        System.out.println(g.getCurrentPlayer());
        System.out.println(Arrays.deepToString(g.getCurrentPlayer().getActiveFields()));
        g.getCurrentPlayer().play(2,2,0,2);

        System.out.println(g.draw());
        System.out.println(Arrays.deepToString(g.getCurrentPlayer().getActiveFields()));

         */
        System.out.println(g.getWinner());
    }

    static int getInt(String message) {
        System.out.print(message);
        int i = 0;
        try {
            i = Integer.parseInt(br.readLine());
        } catch(NumberFormatException nfe) {
            System.err.println("Invalid Format!");
            i = getInt(message);
        } catch (IOException e) {
            throw new RuntimeException("cant read console");
        }
        return i;
    }
}
