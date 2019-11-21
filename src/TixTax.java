import de.undertrox.jtixtax.game.Game;
import de.undertrox.jtixtax.ai.Player;
import de.undertrox.jtixtax.ai.*;


public class TixTax {
    public static void main(String[] args) {
        Player p1 = new RandomAI("RandomAI");
        Player p2 = new WeightedAI("Name",
                                   new RandomAI(""), 80,
                                   new CommandLinePlayer(""), 20);
        Game g = new Game(p1, p2);
        while (!g.hasEnded()) {
            g.playOneTurn();
            System.out.println(g.draw());
        }
        System.out.println(g.status());
    }
}
