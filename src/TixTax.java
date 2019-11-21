import de.undertrox.jtixtax.Game;
import de.undertrox.jtixtax.Player;
import de.undertrox.jtixtax.ai.*;


public class TixTax {
    public static void main(String[] args) {
        Player p1 = new RandomAI("RandomAI");
        Player p2 = new WeightedAI("Name",
                                   new RandomAI(""), 80,
                                   new CommandLinePlayer(""), 20);
        Game g = new Game(p1, p2);
        System.out.println(g
                               .nextState(0,0,0,0)
                               .nextState(0,0,2,2)
                               .draw());
        System.out.println();
        //while (!g.hasEnded()) {
            g.playOneTurn();
            //System.out.println(g.draw());
        //}
        System.out.println(g.draw());
        System.out.println(g.status());
    }
}
