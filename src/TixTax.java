import de.undertrox.tixtax.Game;
import de.undertrox.tixtax.ai.ConsolePlayer;
import de.undertrox.tixtax.ai.RandomAI;

public class TixTax {
    public static void main(String[] args) {
        Game g = new Game(new ConsolePlayer("Silas"), new RandomAI("RandomAI"));
        while (!g.hasEnded()) {
            try {
                g.playOneTurn();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        System.out.println(g.getWinner());
    }
}
