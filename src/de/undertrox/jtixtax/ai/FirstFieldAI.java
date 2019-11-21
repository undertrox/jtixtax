package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.state.GameState;
import de.undertrox.jtixtax.game.Move;

public class FirstFieldAI extends Player {

    public FirstFieldAI(String name) {
        super(name);
    }

    @Override
    public Move play(GameState game) {
        int bigRow = 0, bigCol = 0, smallRow = 0, smallCol = 0;
        while (!game.isValidMove(bigRow, bigCol, smallRow, smallCol)) {
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
