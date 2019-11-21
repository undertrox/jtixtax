package de.undertrox.jtixtax;

public class CellState {
    private Box[][] boxes;
    private Box totalState;
    private boolean isActive;

    public CellState() {
        boxes = new Box[3][3];
        totalState = Box.EMPTY;
        isActive = false;
    }

    public CellState(Box[][] boxes, Box totalState, boolean isActive) {
        this.boxes = boxes;
        this.totalState = totalState;
        this.isActive = isActive;
    }

    private String str(Box b) {
        switch (b) {
            case RED:
                return "O";
            case BLUE:
                return "X";
            case EMPTY:
                return " ";
        }
        return "ERROR";
    }

    public String[] draw() {
        if (getTotalState() == Box.EMPTY || getTotalState() == Box.FULL) {
            if (isActive()) {
                return new String[]{
                    "╔════════════╗",
                    "║ " + str(boxes[0][0]) + " │ " + str(boxes[0][1]) + " │ " + str(boxes[0][2]) + " ║",
                    "╟────────────╢",
                    "║ " + str(boxes[1][0]) + " │ " + str(boxes[1][1]) + " │ " + str(boxes[1][2]) + " ║",
                    "╟────────────╢",
                    "║ " + str(boxes[2][0]) + " │ " + str(boxes[2][1]) + " │ " + str(boxes[2][2]) + " ║",
                    "╚════════════╝"
                };
            }
            return new String[]{
                "┌────────────┐",
                "│ " + str(boxes[0][0]) + " │ " + str(boxes[0][1]) + " │ " + str(boxes[0][2]) + " │",
                "├────────────┤",
                "│ " + str(boxes[1][0]) + " │ " + str(boxes[1][1]) + " │ " + str(boxes[1][2]) + " │",
                "├────────────┤",
                "│ " + str(boxes[2][0]) + " │ " + str(boxes[2][1]) + " │ " + str(boxes[2][2]) + " │",
                "└────────────┘"
            };

        }
        if (getTotalState() == Box.RED) {
            return new String[]{
                "┌────────────┐",
                "│   ____    │",
                "│ /      \\  │",
                "│ |      |  │",
                "│ |      |  │",
                "│  \\____/   │",
                "└────────────┘"
            };
        }
        return new String[]{
            "┌────────────┐",
            "│   \\   /   │",
            "│    \\ /    │",
            "│     X     │",
            "│    / \\    │",
            "│   /   \\   │",
            "└────────────┘"
        };
    }

    public boolean isActive() {
        return isActive;
    }

    public Box getBox(int row, int col) {
        return boxes[row][col];
    }

    public Box getTotalState() {
        return totalState;
    }

    public boolean canSet(int row, int col) {
        return isActive() && totalState == Box.EMPTY
            && getBox(row, col) == Box.EMPTY;
    }
}
