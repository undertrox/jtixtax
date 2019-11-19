import de.undertrox.jtixtax.Game;
import de.undertrox.jtixtax.Player;
import de.undertrox.jtixtax.ai.RandomAI;

public class TixTax {
    public static void main(String[] args) {
        Game g = new Game(new RandomAI("RandomAII"), new RandomAI("RandomAI"));
        while (!g.hasEnded()) {
            try {
                g.playOneTurn();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
