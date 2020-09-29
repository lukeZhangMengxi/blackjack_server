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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mengxi.blackjack_server.db.service.PlayerService;
import mengxi.blackjack_server.game.Game;
import mengxi.blackjack_server.game.GameImpl;
import mengxi.blackjack_server.http_msg.ResultResponse;
import mengxi.blackjack_server.http_msg.StatusResponse;
import mengxi.blackjack_server.db.entity.Player;

@SpringBootApplication
@RestController
public class BlackjackServerApplication {

	private PlayerService playerService;
	private Map<UUID, Game> games = new HashMap<UUID, Game>();
	private ObjectMapper mapper = new ObjectMapper();

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
	@RequestMapping(method = RequestMethod.GET, value = "/game/{gameId}/status", produces = "application/json")
	public ResponseEntity<Object> status(@PathVariable UUID gameId) throws JsonProcessingException {
		if (games.containsKey(gameId)) {
			Game g = games.get(gameId);
			StatusResponse msg = new StatusResponse(
				g.getPlayerBet(), g.getPlayerCards(), g.getDealerCards()
			);
			return new ResponseEntity<>(mapper.writeValueAsString(msg), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@PostMapping("/game/{gameId}/bet")
	public ResponseEntity<Object> bet(@RequestParam UUID playerId, @RequestParam Integer bet,
			@PathVariable UUID gameId) {
		if (games.containsKey(gameId)) {
			Game g = games.get(gameId);
			try {
				g.setPlayerBet(bet);
				playerService.updateDeposit(playerId, 0 - bet);
				return new ResponseEntity<>(null, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@PostMapping("/game/{gameId}/hit")
	public ResponseEntity<Object> hit(@RequestParam UUID playerId, @PathVariable UUID gameId) {
		if (games.containsKey(gameId)) {
			Game g = games.get(gameId);
			g.serveRandomCard(playerId);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
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

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@RequestMapping(method = RequestMethod.GET, value = "/game/{gameId}/result", produces = "application/json")
	public ResponseEntity<Object> close(@RequestParam UUID playerId, @PathVariable UUID gameId) {
		if (games.containsKey(gameId)) {
			Game g = games.get(gameId);
			int result = g.getResult(playerId);

			try {
				long newDeposit = -1;
				// If win, return double bet
				if (result == 1) newDeposit = playerService.updateDeposit(playerId, 2*g.getPlayerBet());
				// If tied, return bet
				else if (result == 0) newDeposit = playerService.updateDeposit(playerId, g.getPlayerBet());
				// else, return 0
				else newDeposit = playerService.getDeposit(playerId);
				
				ResultResponse msg = new ResultResponse(result, newDeposit);
				return new ResponseEntity<>(mapper.writeValueAsString(msg), HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	public static void main(String[] args) {
		SpringApplication.run(BlackjackServerApplication.class, args);
	}

}
