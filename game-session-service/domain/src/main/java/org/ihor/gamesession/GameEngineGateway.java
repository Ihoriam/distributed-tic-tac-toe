package org.ihor.gamesession;

public interface GameEngineGateway {
    GameState createGame();

    GameState makeMove(String gameId, int row, int col, SessionPlayer player);
    GameState getGame(String gameId);
}
