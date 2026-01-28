package org.ihor.gamesession.gameengine;

import org.ihor.gamesession.GameEngineGateway;
import org.ihor.gamesession.exceptions.GameEngineException;
import org.ihor.gamesession.models.GameState;
import org.ihor.gamesession.models.SessionGameStatus;
import org.ihor.gamesession.models.SessionPlayer;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class GameEngineClient implements GameEngineGateway {

    private final RestClient restClient;

    public GameEngineClient(String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public GameState createGame() {
        try {
            GameEngineResponse response = restClient.post()
                .uri("/games")
                    .retrieve()
                .body(GameEngineResponse.class);
            return toGameState(response);
        } catch (Exception e) {
            throw new GameEngineException("Failed to create game", e);
        }
    }

    @Override
    public GameState makeMove(String gameId, int row, int col, SessionPlayer player) {
        try {
            GameEngineResponse response = restClient.post()
                    .uri("/games/{gameId}/move", gameId)
                .body(Map.of("row", row, "col", col, "player", player.name()))
                    .retrieve()
                .body(GameEngineResponse.class);
            return toGameState(response);
        } catch (Exception e) {
            throw new GameEngineException("Failed to make move for game: " + gameId, e);
        }
    }

    private GameState toGameState(GameEngineResponse response) {
        return new GameState(
            response.gameId(),
            response.board(),
            SessionPlayer.valueOf(response.currentTurn()),
            SessionGameStatus.valueOf(response.status())
        );
    }

    private record GameEngineResponse(String gameId, String[] board, String currentTurn, String status) {
    }
}
