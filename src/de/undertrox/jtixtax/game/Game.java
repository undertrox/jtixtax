package de.undertrox.jtixtax.game;

import de.undertrox.jtixtax.game.state.CellState;
import de.undertrox.jtixtax.game.state.GameState;
import de.undertrox.jtixtax.ai.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Cell[][] board;
    private Player p1, p2;
    private Player winner;
    private Player currentPlayer;
    private boolean tie;


    /**
     * Initialisiert das Spielfeld mit leeren Cells
     */
    public Game(Player player1, Player player2) {
        board = new Cell[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
        this.p1 = player1;
        p1.init(Box.RED);
        this.p2 = player2;
        p2.init(Box.BLUE);
        currentPlayer = p1;
    }

    /**
     * Fuehrt einen Zug aus
     */
    public void playOneTurn() {
        Move m = currentPlayer.play(getState());
        if (isValidMove(m)) {
            set(m, currentPlayer);
            nextTurn();
        } else {
            throw new RuntimeException("Invalid Move");
        }
    }

    /**
     * Setzt ein Kreuz oder Kreis an den eingegebenen Koordinaten und
     * initiiert den naechsten Zug. Wenn der Spieler nicht am Zug ist, wird
     * ein Fehler erzeugt.
     *
     * @param m: Zug
     */
    private void set(Move m, Player p) {
        if (p == currentPlayer) {
            board[m.bigRow][m.bigCol].setBox(m.smallRow, m.smallCol, p.getColor());
            clearActive();
            setActive(m.smallRow, m.smallCol);
        } else {
            throw new RuntimeException("Tried to Play without being current Player");
        }
    }

    /**
     * Initiiert den naechsten Zug und gibt den entsprechenden Spieler, der jetzt dran
     * ist, zurueck.
     */
    private void nextTurn() {
        checkBoxes();
        if (winner != null) {
            clearActive();
            return;
        } else if (getEmptyFields().size() == 0) {
            tie = true;
            clearActive();
            return;
        }
        if (currentPlayer == p1) {
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
    }

    /**
     * deaktiviert alle Cells, sollte nicht manuell aufgerufen werden.
     */
    private void clearActive() {
        for (Cell[] field : board) {
            for (Cell box : field) {
                if (box.isActive()) {
                    box.setActive(false);
                }
            }
        }
    }

    /**
     * Aktiviert die Cell an (row, col).
     * wenn die Cell schon ausgefuellt ist, werden alle noch nicht ausgefuellten
     * Cells aktiviert
     */
    private void setActive(int row, int col) {
        if (board[row][col].getTotalState() == Box.EMPTY) {
            board[row][col].setActive(true);
        } else {
            for (Cell field : getEmptyFields()) {
                field.setActive(true);
            }
        }
    }

    public Game copy() {
        Game newGame = new Game(p1, p2);
        newGame.board = new Cell[3][3];
        for (int i = 0; i<3; i++) {
            for (int j = 0; j<3; j++) {
                newGame.board[i][j] = board[i][j].copy();
            }
        }
        newGame.currentPlayer = currentPlayer;
        newGame.winner = winner;
        newGame.tie = tie;
        return newGame;
    }

    private void checkBoxes() {
        Box winner = getState().getWinner();
        if (winner != null) {
            win(winner);
        }
    }

    /**
     * gibt eine Liste mit allen Cells, in denen noch niemand gewonnen hat
     * zurueck.
     */
    private List<Cell> getEmptyFields() {
        List<Cell> res = new ArrayList<>();
        for (Cell[] field : board) {
            for (Cell cell : field) {
                if (cell.getTotalState() == Box.EMPTY) {
                    res.add(cell);
                }
            }
        }
        return res;
    }

    private void win(Box color) {
        if (color == Box.RED) {
            win(p1);
        } else if (color == Box.BLUE) {
            win(p2);
        } else {
            System.err.println("This shouldn't happen (someone called win with an invalid color");
        }
    }

    private void win(Player p) {
        winner = p;
    }

    public String status() {
        if (hasEnded()) {
            if (winner != null) {
                return "Game ended. " + getWinner() + " won.";
            } else if (tie) {
                return "Game ended in a tie. ";
            }
        }
        return "Game is running. " + currentPlayer + " is currently playing.";
    }

    public boolean hasEnded() {
        return winner != null || tie;
    }

    public Player getWinner() {
        return winner;
    }

    public String draw() {
        return getState().draw() + "\n" +
                getCurrentPlayer();
    }

    public Player getCurrentPlayer() {
        return hasEnded() ? null : currentPlayer;
    }

    public GameState getState() {
        CellState[][] cellStates = new CellState[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellStates[i][j] = board[i][j].getState();
            }
        }
        return new GameState(cellStates, this);
    }

    public GameState nextState(int bigRow, int bigCol, int smallRow, int smallCol) {
        Game g = this.copy();
        g.set(new Move(bigRow, bigCol, smallRow, smallCol), currentPlayer);
        g.nextTurn();
        return g.getState();
    }

    /**
     * gibt zurueck, ob ein Spieler an der entsprechenden Position ein Kreuz oder
     * Kreis setzen koennte
     */
    public boolean isValidMove(int bigRow, int bigCol, int smallRow, int smallCol) {
        if (bigRow > 2 || bigRow < 0 || bigCol > 2 || bigCol < 0 ||
                smallRow > 2 || smallRow < 0 || smallCol > 2 || smallCol < 0)
            return false;
        return board[bigRow][bigCol].canSetBox(smallRow, smallCol);
    }

    public boolean isValidMove(Move move) {
        return isValidMove(move.bigRow, move.bigCol, move.smallRow, move.smallCol);
    }
}
