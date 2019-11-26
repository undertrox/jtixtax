package de.undertrox.jtixtax.game.state;

import de.undertrox.jtixtax.game.Box;
import de.undertrox.jtixtax.game.Game;
import de.undertrox.jtixtax.game.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameState {
    private CellState[][] state;
    private Game parentGame;
    private GameState previousState;

    public GameState(CellState[][] state, Game g) {
        this.state = state;
        this.parentGame = g;
    }
    private void setPreviousState(GameState prev) {
        previousState = prev;
    }

    public GameState getPreviousState() {
        return previousState;
    }

    /**
     * Returns the Box state at the corresponding Coordinates
     */
    public Box getBox(int bigRow, int bigCol, int smallRow, int smallCol) {
        return getCell(bigRow, bigCol).getBox(smallRow, smallCol);
    }

    public CellState getCell(int row, int col) {
        return state[row][col];
    }

    /**
     * returns whether the Cell at the coordinates is currently active.
     */
    public boolean isActive(int bigRow, int bigCol) {
        return state[bigRow][bigCol].isActive();
    }

    /**
     * returns whether setting a Cross or Circle at the coords is possible
     */
    public boolean canSet(int bigRow, int bigCol, int smallRow, int smallCol) {
        return state[bigRow][bigCol].canSet(smallRow, smallCol);
    }

    public String draw() {
        String[] res = new String[21];
        Arrays.fill(res, "");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String[] next = state[i][j].draw();
                for (int k = 0; k < 7; k++) {
                    res[7 * i + k] += next[k];
                }
            }
        }
        return Arrays.toString(res)
                     .replace(", ", "\n")
                     .replace("[", "")
                     .replace("]", "");

    }

    /**
     * returns the Gamestate as it would be after setting a Cross or Circle at
     * the specified position without changing the actual game. returns null
     * if nothing can be done on the Specified coordinates.
     */
    public GameState nextState(int bigRow, int bigCol, int smallRow, int smallCol) {
        if (isValidMove(bigRow, bigCol, smallRow, smallCol)) {
            GameState next = parentGame.copy().nextState(bigRow, bigCol, smallRow, smallCol);
            next.setPreviousState(this);
            return next;
        }
        System.out.println(draw());
        System.out.println(new Move(bigRow,bigCol,smallRow,smallCol));

        return null;
    }

    /**
     * returns a List of Moves with the Coordinates of all Active Cells
     * on the Board.
     * @return
     */
    public List<Move> getActiveCellCoords() {
        ArrayList<Move> cells = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            for (int j = 0; j<3; j++) {
                if (state[i][j].isActive()){
                    cells.add(new Move(i,j));
                }
            }
        }
        return cells;
    }

    /**
     * returns the Gamestate as it would be after setting a Cross or Circle at
     * the specified position without changing the actual game. returns null
     * if nothing can be done on the Specified coordinates.
     */
    public GameState nextState(Move move) {
        return nextState(move.bigRow, move.bigCol, move.smallRow, move.smallCol);
    }

    /**
     * returns a List with all Valid Moves for the current GameState
     */
    public List<Move> getValidMoves() {
        ArrayList<Move> validMoves = new ArrayList<>();
        // Sorry
        for (int i = 0; i<3; i++) {
            for (int j = 0; j <3; j++) {
                for (int k = 0; k<3; k++) {
                    for (int m = 0; m<3; m++) {
                        if (isValidMove(i,j,k,m)) {
                            validMoves.add(new Move(i,j,k,m));
                        }
                    }
                }
            }
        }
        return validMoves;
    }

    public Box getWinner() {
        for (int i = 0; i < state.length; i++) {
            if (state[i][0].getTotalState() == state[i][1].getTotalState()
                && state[i][1].getTotalState() == state[i][2].getTotalState()
                && state[i][1].getTotalState() != Box.EMPTY
                && state[i][1].getTotalState() != Box.FULL) {
                return state[i][0].getTotalState();
            } else if (state[0][i].getTotalState() == state[1][i].getTotalState()
                && state[1][i].getTotalState() == state[2][i].getTotalState()
                && state[1][i].getTotalState() != Box.EMPTY
                && state[1][i].getTotalState() != Box.FULL) {
                return state[0][i].getTotalState();
            }
        }
        if (state[0][0].getTotalState() == state[1][1].getTotalState() &&
            state[1][1].getTotalState() == state[2][2].getTotalState()
            && state[0][0].getTotalState() != Box.EMPTY
            && state[0][0].getTotalState() != Box.FULL) {
            return state[0][0].getTotalState();
        } else if (state[0][2].getTotalState() == state[1][1].getTotalState()
            && state[1][1].getTotalState() == state[2][0].getTotalState()
            && state[1][1].getTotalState() != Box.EMPTY
            && state[1][1].getTotalState() != Box.FULL) {
            return state[2][0].getTotalState();
        }
        return null;
    }

    /**
     * returns whether setting a Cross or Circle at the specified Coords is
     * a valid move.
     *
     */
    public boolean isValidMove(int bigRow, int bigCol, int smallRow, int smallCol) {
        return parentGame.isValidMove(bigRow, bigCol, smallRow, smallCol);
    }

    /**
     * returns whether setting a Cross or Circle at the specified Coords is
     * a valid move.
     *
     */
    public boolean isValidMove(Move move) {
        return isValidMove(move.bigRow, move.bigCol, move.smallRow, move.smallCol);
    }
}
