package org.ihor.gamesession.websocket;

import org.ihor.gamesession.GameState;
import org.ihor.gamesession.Move;
import org.ihor.gamesession.SimulationUpdatePublisher;
import org.ihor.gamesession.websocket.dto.MoveUpdateMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class WebSocketSimulationPublisher implements SimulationUpdatePublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketSimulationPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void publishMoveUpdate(String sessionId, Move move, GameState state, int moveNumber) {
        MoveUpdateMessage message = new MoveUpdateMessage(
            sessionId,
            move.row(),
            move.col(),
            move.player(),
            state.board(),
            state.status().name(),
            moveNumber,
            false,
            null
        );
        messagingTemplate.convertAndSend("/topic/session/" + sessionId, message);
    }

    @Override
    public void publishSimulationComplete(String sessionId, GameState finalState, int totalMoves) {
        String resultMessage = switch (finalState.status()) {
            case X_WON -> "Player X wins!";
            case O_WON -> "Player O wins!";
            case DRAW -> "Game ended in a draw.";
            default -> "Game over.";
        };

        MoveUpdateMessage message = new MoveUpdateMessage(
            sessionId,
            -1,
            -1,
            null,
            finalState.board(),
            finalState.status().name(),
            totalMoves,
            true,
            resultMessage
        );
        messagingTemplate.convertAndSend("/topic/session/" + sessionId, message);
    }
}
