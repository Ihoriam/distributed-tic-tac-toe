package org.ihor.gameengine.models;

public class Board {

    private final Player[] cells = new Player[9];

    public Player getCell(int row, int col) {
        return cells[row * 3 + col];
    }

    public void setCell(int row, int col, Player player) {
        cells[row * 3 + col] = player;
    }

    public boolean isCellEmpty(int row, int col) {
        return cells[row * 3 + col] == null;
    }

    public boolean isFull() {
        for (Player cell : cells) {
            if (cell == null) return false;
        }
        return true;
    }

    public Player[] getCells() {
        return cells.clone();
    }
}
