package mengxi.blackjack_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("/health")
    public String health() {
        return String.format("OK");
    }
}
