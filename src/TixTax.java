import de.undertrox.jtixtax.Game;
import de.undertrox.jtixtax.Player;
import de.undertrox.jtixtax.ai.CommandLinePlayer;
import de.undertrox.jtixtax.ai.RandomAI;

public class TixTax {
    public static void main(String[] args) {
        Player p1 = new RandomAI("RandomAI");
        Player p2 = new CommandLinePlayer("Name");
        Game g = new Game(p1, p2);
        while (!g.hasEnded()) {
            g.playOneTurn();
        }
        System.out.println(g.status());
    }
}
