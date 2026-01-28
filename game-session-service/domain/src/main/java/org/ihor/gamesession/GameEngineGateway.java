package org.ihor.gamesession;

public interface GameEngineGateway {
    GameState createGame();
    GameState makeMove(String gameId, int row, int col, String player);
    GameState getGame(String gameId);
}
