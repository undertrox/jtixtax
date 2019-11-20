import de.undertrox.jtixtax.Game;
import de.undertrox.jtixtax.Player;
import de.undertrox.jtixtax.ai.CommandLinePlayer;
import de.undertrox.jtixtax.ai.RandomAI;

import java.io.IOException;

public class TixTax {
    public static void main(String[] args) {
        int r1 = 0, r2 = 0, tie = 0;
        Player p1 = new RandomAI("RandomAI");
        Player p2 = new CommandLinePlayer("Kevin");
        Game g = new Game(p1, p2);
        while (!g.hasEnded()) {
            g.playOneTurn();
            System.out.println(g.draw());
        }
        System.out.println(g.status());
    }
}
