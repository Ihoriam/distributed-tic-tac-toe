package org.ihor.ui.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @Value("${game.session.base-url}")
    private String sessionBaseUrl;

    @Value("${game.session.ws-url}")
    private String sessionWsUrl;

    @GetMapping("/")
    public String game(Model model) {
        model.addAttribute("sessionApiUrl", sessionBaseUrl);
        model.addAttribute("wsUrl", sessionWsUrl);
        return "game";
    }
}
