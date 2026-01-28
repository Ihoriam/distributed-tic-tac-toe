package org.ihor.gamesession.rest.dto;

import org.ihor.gamesession.models.Session;

import java.util.List;

public record SessionResponse(
        String sessionId,
        String gameId,
        List<MoveDto> moveHistory,
        GameStateDto currentGameState,
        boolean completed
) {
    public static SessionResponse from(Session session) {
        return new SessionResponse(
                session.getSessionId(),
                session.getGameId(),
                session.getMoveHistory().stream().map(MoveDto::from).toList(),
                GameStateDto.from(session.getCurrentGameState()),
                session.isCompleted()
        );
    }
}
