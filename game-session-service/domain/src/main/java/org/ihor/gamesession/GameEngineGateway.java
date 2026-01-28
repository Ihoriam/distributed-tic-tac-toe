package org.ihor.gamesession;

public interface GameEngineGateway {
    GameState createGame(String gameId);
    GameState makeMove(String gameId, int row, int col, String player);
    GameState getGame(String gameId);
}
