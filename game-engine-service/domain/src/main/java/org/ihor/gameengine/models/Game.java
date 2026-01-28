package org.ihor.gameengine.models;

import org.ihor.gameengine.exceptions.GameOverException;
import org.ihor.gameengine.exceptions.InvalidMoveException;

public class Game {

    private static final int[][] WIN_LINES = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };

    private final String gameId;
    private final Board board;
    private Player currentTurn;
    private GameStatus status;

    public Game(String gameId) {
        this.gameId = gameId;
        this.board = new Board();
        this.currentTurn = Player.X;
        this.status = GameStatus.PLAYING;
    }

    public synchronized MoveResult makeMove(int row, int col, Player player) {
        if (status != GameStatus.PLAYING) {
            throw new GameOverException("Game is already over");
        }
        if (row < 0 || row > 2 || col < 0 || col > 2) {
            throw new InvalidMoveException("Position out of bounds");
        }
        if (player != currentTurn) {
            throw new InvalidMoveException("Not your turn, expected " + currentTurn);
        }
        if (!board.isCellEmpty(row, col)) {
            throw new InvalidMoveException("Cell is already occupied");
        }

        board.setCell(row, col, player);

        if (checkWin(player)) {
            status = player == Player.X ? GameStatus.X_WON : GameStatus.O_WON;
        } else if (board.isFull()) {
            status = GameStatus.DRAW;
        } else {
            currentTurn = currentTurn.opposite();
        }
        return new MoveResult(player, row, col, status);
    }

    private boolean checkWin(Player player) {
        Player[] cells = board.getCells();
        for (int[] line : WIN_LINES) {
            if (cells[line[0]] == player && cells[line[1]] == player && cells[line[2]] == player) {
                return true;
            }
        }
        return false;
    }

    public String getGameId() {
        return gameId;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public GameStatus getStatus() {
        return status;
    }
}
