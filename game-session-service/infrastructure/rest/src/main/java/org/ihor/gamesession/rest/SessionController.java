package org.ihor.gamesession.rest;

import org.ihor.gamesession.Session;
import org.ihor.gamesession.SessionService;
import org.ihor.gamesession.rest.dto.SessionResponse;
import org.ihor.gamesession.rest.dto.SimulationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
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
}
