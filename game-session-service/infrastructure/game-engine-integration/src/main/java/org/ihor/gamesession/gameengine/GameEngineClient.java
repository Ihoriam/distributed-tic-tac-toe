package org.ihor.gamesession.gameengine;

import org.ihor.gamesession.GameEngineGateway;
import org.ihor.gamesession.GameState;
import org.ihor.gamesession.exceptions.GameEngineException;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class GameEngineClient implements GameEngineGateway {

    private final RestClient restClient;

    public GameEngineClient(String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public GameState createGame(String gameId) {
        try {
            return restClient.post()
                    .uri("/games/{gameId}", gameId)
                    .retrieve()
                    .body(GameState.class);
        } catch (Exception e) {
            throw new GameEngineException("Failed to create game: " + gameId, e);
        }
    }

    @Override
    public GameState makeMove(String gameId, int row, int col, String player) {
        try {
            return restClient.post()
                    .uri("/games/{gameId}/move", gameId)
                    .body(Map.of("row", row, "col", col, "player", player))
                    .retrieve()
                    .body(GameState.class);
        } catch (Exception e) {
            throw new GameEngineException("Failed to make move for game: " + gameId, e);
        }
    }

    @Override
    public GameState getGame(String gameId) {
        try {
            return restClient.get()
                    .uri("/games/{gameId}", gameId)
                    .retrieve()
                    .body(GameState.class);
        } catch (Exception e) {
            throw new GameEngineException("Failed to get game: " + gameId, e);
        }
    }
}
