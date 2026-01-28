package org.ihor.gamesession;

import org.ihor.gamesession.models.GameState;
import org.ihor.gamesession.models.SessionPlayer;

public interface GameEngineGateway {
    GameState createGame();

    GameState makeMove(String gameId, int row, int col, SessionPlayer player);
}
