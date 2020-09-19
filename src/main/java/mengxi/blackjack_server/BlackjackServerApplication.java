package mengxi.blackjack_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import mengxi.blackjack_server.db.service.PlayerService;
import mengxi.blackjack_server.game.Game;
import mengxi.blackjack_server.game.GameImpl;
import mengxi.blackjack_server.db.entity.Player;

@SpringBootApplication
@RestController
public class BlackjackServerApplication {

	private PlayerService playerService;
	private Map<UUID, Game> games = new HashMap<UUID, Game>();

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

	@GetMapping("/game/status")
	public List<List<String>> status(@RequestParam UUID gameId) {
		return new ArrayList<>() {
			private static final long serialVersionUID = 1L;
			{
				if (games.containsKey(gameId)) {
					this.add(games.get(gameId).getPlayerCards());
					this.add(games.get(gameId).getDealerCards());
				}
			}
		};
	}

	@PostMapping("/game/start")
	public UUID start(@RequestParam UUID playerId) {
		Game g = new GameImpl();
		g.start(playerId);
		games.put(g.getGameId(), g);
		return g.getGameId();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlackjackServerApplication.class, args);
	}

}
