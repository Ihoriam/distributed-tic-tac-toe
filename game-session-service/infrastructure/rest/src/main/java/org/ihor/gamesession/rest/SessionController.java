package org.ihor.gamesession.rest;

import org.ihor.gamesession.Session;
import org.ihor.gamesession.SessionService;
import org.ihor.gamesession.SimulationUpdatePublisher;
import org.ihor.gamesession.rest.dto.SessionResponse;
import org.ihor.gamesession.rest.dto.SimulationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final SimulationUpdatePublisher simulationUpdatePublisher;

    public SessionController(SessionService sessionService, SimulationUpdatePublisher simulationUpdatePublisher) {
        this.sessionService = sessionService;
        this.simulationUpdatePublisher = simulationUpdatePublisher;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse createSession() {
        return SessionResponse.from(sessionService.createSession());
    }

    @PostMapping("/{sessionId}/simulate")
    public SimulationResponse simulateGame(@PathVariable("sessionId") String sessionId) {
        Session session = sessionService.simulateGame(sessionId);
        return SimulationResponse.from(session);
    }

    @GetMapping("/{sessionId}")
    public SessionResponse getSession(@PathVariable("sessionId") String sessionId) {
        return SessionResponse.from(sessionService.getSession(sessionId));
    }

    @PostMapping("/{sessionId}/simulate-async")
    public ResponseEntity<Void> simulateGameAsync(
        @PathVariable("sessionId") String sessionId,
        @RequestParam(defaultValue = "500") int delayMs) {
        CompletableFuture.runAsync(() ->
            sessionService.simulateGameWithSpeed(sessionId, delayMs, simulationUpdatePublisher)
        );
        return ResponseEntity.accepted().build();
    }
}
