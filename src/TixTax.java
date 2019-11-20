import de.undertrox.jtixtax.Game;
import de.undertrox.jtixtax.Player;
import de.undertrox.jtixtax.ai.*;

public class TixTax {
    public static void main(String[] args) {
        Player p1 = new WeightedAI("RandomAI",
                                   new FirstFieldAI(""), 50,
                                   new LastFieldAI(""), 50);
        Player p2 = new WeightedAI("Name",
                                   new RandomAI(""), 90,
                                   new CommandLinePlayer(""), 10);
        Game g = new Game(p1, p2);
        while (!g.hasEnded()) {
            g.playOneTurn();
            System.out.println(g.draw());
        }
        System.out.println(g.status());
    }
}
