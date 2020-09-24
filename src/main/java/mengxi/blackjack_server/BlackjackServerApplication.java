package mengxi.blackjack_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@GetMapping("/game/{gameId}/status")
	@ResponseBody
	public List<List<String>> status(@PathVariable UUID gameId) {
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

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@PostMapping("/game/start")
	@ResponseBody
	public UUID start(@RequestParam UUID playerId) {
		Game g = new GameImpl();
		g.start(playerId);
		games.put(g.getGameId(), g);
		return g.getGameId();
	}

	@PostMapping("/game/{gameId}/hit")
	public ResponseEntity<Object> hit(@RequestParam UUID playerId, @PathVariable UUID gameId) {
		if (games.containsKey(gameId)) {
			Game g = games.get(gameId);
			g.serveRandomCard(playerId);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/game/{gameId}/stand")
	public ResponseEntity<Object> stand(@RequestParam UUID playerId, @PathVariable UUID gameId) {
		if (games.containsKey(gameId)) {
			Game g = games.get(gameId);
			while (g.cardSum(g.getDealerCards()) < 17) {
				g.serveRandomCard(g.getDelearId());
			}
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/game/{gameId}/result")
	public ResponseEntity<Integer> close(@RequestParam UUID playerId, @PathVariable UUID gameId) {
		if (games.containsKey(gameId)) {
			return new ResponseEntity<>(games.get(gameId).getResult(playerId), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	public static void main(String[] args) {
		SpringApplication.run(BlackjackServerApplication.class, args);
	}

}
