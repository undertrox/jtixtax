package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.Game;
import de.undertrox.jtixtax.Move;
import de.undertrox.jtixtax.Player;

public class FirstFieldAI extends Player {

    public FirstFieldAI(String name) {
        super(name);
    }

    @Override
    public Move play(Game game) {
        int bigRow = 0, bigCol = 0, smallRow = 0, smallCol = 0;
        while (!canSet(bigRow, bigCol, smallRow, smallCol)) {
            smallRow++;
            if (smallRow>2) {
                smallRow = 0;
                smallCol++;
                if (smallCol>2) {
                    smallCol = 0;
                    bigRow++;
                    if (bigRow >2){
                        bigRow = 0;
                        bigCol++;
                        if (bigCol>2) {
                            bigCol = 0;
                        }
                    }
                }
            }
        };
        return new Move(bigRow, bigCol, smallRow, smallCol);    }
}
