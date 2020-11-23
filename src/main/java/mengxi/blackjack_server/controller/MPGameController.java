package mengxi.blackjack_server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mengxi.blackjack_server.game.MultiPlayerGame;
import mengxi.blackjack_server.game.MultiPlayerGameImpl;
import mengxi.blackjack_server.http_msg.MPGameListRsp;

@RestController
@RequestMapping("mpgame")
public class MPGameController {

	private Map<UUID, MultiPlayerGame> games = new HashMap<UUID, MultiPlayerGame>();


    @GetMapping("/health")
	public String health() {
		return String.format("OK");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create", produces = "application/json")
	public ResponseEntity<Object> create(@RequestParam UUID ownerId) {
		MultiPlayerGame g = new MultiPlayerGameImpl(ownerId, "");
		games.put(g.getGameId(), g);
		return new ResponseEntity<>(g.getGameId(), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list", produces = "application/json")
	public ResponseEntity<Object> listAll() {
		MPGameListRsp msg = new MPGameListRsp(new HashMap<UUID, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				for (MultiPlayerGame g : games.values()) {
					this.put(g.getGameId(), g.listPlayerNames());
				}
			}
		});
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/join/{gameId}", produces = "application/json")
	public ResponseEntity<Object> join(@PathVariable UUID gameId, @RequestParam UUID playerId) {
		MultiPlayerGame g = games.get(gameId);
		g.addPlayer(playerId, "");
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
