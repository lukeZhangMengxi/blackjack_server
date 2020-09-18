package mengxi.blackjack_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import mengxi.blackjack_server.db.service.PlayerService;
import mengxi.blackjack_server.game.Game;
import mengxi.blackjack_server.game.GameImpl;
import mengxi.blackjack_server.http_msg.GameStartResponse;
import mengxi.blackjack_server.db.entity.Player;

@SpringBootApplication
@RestController
public class BlackjackServerApplication {

	private PlayerService playerService;

	@Autowired
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/health")
	public String health() {
		return String.format("OK");
	}

	@GetMapping("/allplayers")
	public List<Player> all() {
		return playerService.getAll();
	}

	@PostMapping("/start")
	public GameStartResponse start() {
		Game g = new GameImpl();
		g.start(UUID.randomUUID());

		return new GameStartResponse(
			g.getGameId(), g.getPlayerCards(), g.getDealerCards()
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(BlackjackServerApplication.class, args);
	}

}
