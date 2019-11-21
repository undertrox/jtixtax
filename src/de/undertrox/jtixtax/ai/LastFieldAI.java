package de.undertrox.jtixtax.ai;

import de.undertrox.jtixtax.game.state.GameState;
import de.undertrox.jtixtax.game.Move;

public class LastFieldAI extends Player {

    public LastFieldAI(String name) {
        super(name);
    }

    public Move play(GameState game) {
        int bigRow = 2, bigCol = 2, smallRow = 2, smallCol = 2;
        while (!game.isValidMove(bigRow, bigCol, smallRow, smallCol)) {
            smallRow--;
            if (smallRow<0) {
                smallRow = 2;
                smallCol--;
                if (smallCol<0) {
                    smallCol = 2;
                    bigRow--;
                    if (bigRow < 0){
                        bigRow = 2;
                        bigCol--;
                        if (bigCol<0) {
                            bigCol = 2;
                        }
                    }
                }
            }
        };
        return new Move(bigRow, bigCol, smallRow, smallCol);
    }
}
