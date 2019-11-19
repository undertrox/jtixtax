package de.undertrox.tixtax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private TicTacToe[][] board;
    private Player p1, p2;
    private Player winner;
    private Player currentPlayer;
    private boolean tie;


    /**
     * Initialisiert das Spielfeld mit leeren de.undertrox.tixtax.TicTacToe-Feldern
     */
    public Game(Player player1, Player player2) {
        board = new TicTacToe[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new TicTacToe(this, i, j);
            }
        }
        this.p1 = player1;
        p1.init(Box.RED, this);
        this.p2 = player2;
        p2.init(Box.BLUE, this);
        currentPlayer = p1;
    }

    /**
     * Fuehrt einen Zug aus
     */
    public void playOneTurn() {
        set(currentPlayer.play(this), currentPlayer);
        nextTurn();
    }

    /**
     * Setzt ein Kreuz oder Kreis an den eingegebenen Koordinaten und
     * initiiert den naechsten Zug. Wenn der Spieler nicht am Zug ist, wird
     * ein Fehler erzeugt.
     *
     * @param c: Koordinaten des Zuges
     */
    private void set(int[] c, Player p) {
        if (p.isActive()) {
            getBoard()[c[0]][c[1]].setBox(c[2], c[3], p.getColor());
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
            System.out.println("Game ended. " + winner + " won.");
            return;
        } else if (getEmptyFields().size() == 0) {
            System.out.println("Game ended in a tie. ");
            tie = true;
        }
        if (currentPlayer == p1) {
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
    }

    public TicTacToe[][] getBoard() {
        return board;
    }

    private void checkBoxes() {
        for (int i = 0; i < board.length; i++) {
            if (board[i][0].getTotalState() == board[i][1].getTotalState()
                    && board[i][1].getTotalState() == board[i][2].getTotalState()
                    && board[i][1].getTotalState() != Box.EMPTY
                    && board[i][1].getTotalState() != Box.TIE) {
                win(board[i][0].getTotalState());
            } else if (board[0][i].getTotalState() == board[1][i].getTotalState()
                    && board[1][i].getTotalState() == board[2][i].getTotalState()
                    && board[1][i].getTotalState() != Box.EMPTY
                    && board[1][i].getTotalState() != Box.TIE) {
                win(board[0][i].getTotalState());
            }
        }
        if (board[0][0].getTotalState() == board[1][1].getTotalState() &&
                board[1][1].getTotalState() == board[2][2].getTotalState()
                && board[0][0].getTotalState() != Box.EMPTY
                && board[0][0].getTotalState() != Box.TIE) {
            win(board[0][0].getTotalState());
        } else if (board[0][2].getTotalState() == board[1][1].getTotalState()
                && board[1][1].getTotalState() == board[2][0].getTotalState()
                && board[1][1].getTotalState() != Box.EMPTY
                && board[1][1].getTotalState() != Box.TIE) {
            win(board[2][0].getTotalState());
        }
    }

    /**
     * gibt eine Liste mit allen TTTs, in denen noch niemand gewonnen hat
     * zurueck.
     */
    private List<TicTacToe> getEmptyFields() {
        List<TicTacToe> res = new ArrayList<>();
        for (TicTacToe[] field : board) {
            for (int j = 0; j < field.length; j++) {
                if (field[j].getTotalState() == Box.EMPTY) {
                    res.add(field[j]);
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

    public String draw() {
        String[] res = new String[21];
        Arrays.fill(res, "");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String[] next = board[i][j].draw();
                for (int k = 0; k < 7; k++) {
                    res[7 * i + k] += next[k];
                }
            }
        }
        return Arrays.toString(res)
                     .replace(", ", "\n")
                     .replace("[", "")
                     .replace("]", "")
                + "\n" +
                getCurrentPlayer();
    }

    public Player getCurrentPlayer() {
        return hasEnded() ? null : currentPlayer;
    }

    public boolean hasEnded() {
        return winner != null || tie;
    }

    public Player getWinner() {
        return winner;
    }

    /**
     * Aktiviert das TTT an (row, col).
     * wenn das TTT schon ausgefuellt ist, werden alle noch nicht ausgefuellten
     * TTTs aktiviert
     */
    public void setActive(int row, int col) {
        if (board[row][col].getTotalState() == Box.EMPTY) {
            board[row][col].setActive(true);
        } else {
            for (TicTacToe field : getEmptyFields()) {
                field.setActive(true);
            }
        }
    }

    /**
     * gibt alle TicTacToe-Felder zurueck, in die im aktuellen Zug
     * gesetzt werden kann.
     */
    public List<TicTacToe> getActiveFields() {
        List<TicTacToe> res = new ArrayList<>();
        for (TicTacToe[] field : board) {
            for (TicTacToe box : field) {
                if (box.isActive()) {
                    res.add(box);
                }
            }
        }
        return res;
    }

    /**
     * deaktiviert alle TTTs, sollte nicht manuell aufgerufen werden.
     */
    public void clearActive() {
        for (TicTacToe[] field : board) {
            for (TicTacToe box : field) {
                if (box.isActive()) {
                    box.setActive(false);
                }
            }
        }
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
}
