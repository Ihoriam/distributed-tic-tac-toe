package org.ihor.gamesession.rest.dto;

import org.ihor.gamesession.Session;

import java.time.Instant;
import java.util.List;

public record SessionResponse(
        String sessionId,
        String gameId,
        List<MoveDto> moveHistory,
        GameStateDto currentGameState,
        Instant createdAt,
        Instant completedAt,
        boolean completed
) {
    public static SessionResponse from(Session session) {
        return new SessionResponse(
                session.getSessionId(),
                session.getGameId(),
                session.getMoveHistory().stream().map(MoveDto::from).toList(),
                GameStateDto.from(session.getCurrentGameState()),
                session.getCreatedAt(),
                session.getCompletedAt(),
                session.isCompleted()
        );
    }
}
