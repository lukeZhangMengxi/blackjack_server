package mengxi.blackjack_server.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mengxi.blackjack_server.db.service.PlayerService;
import mengxi.blackjack_server.game.MultiPlayerGame;
import mengxi.blackjack_server.game.MultiPlayerGameImpl;
import mengxi.blackjack_server.http_msg.MPGameListRsp;
import mengxi.blackjack_server.security.JwtAPI;
import mengxi.blackjack_server.security.JwtAPI.ClaimType;

@RestController
@RequestMapping("mpgame")
public class MPGameController {

	@Autowired
	private PlayerService playerService;

	private Map<UUID, MultiPlayerGame> mpGames = new HashMap<UUID, MultiPlayerGame>();

	@GetMapping("/health")
	public String health() {
		return String.format("OK");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/create", produces = "application/json")
	public ResponseEntity<Object> create(@RequestParam UUID ownerId, @RequestHeader("jwt") String token) {
		if (!JwtAPI.verifyToken(token, ownerId.toString(), ClaimType.PLAYERID)) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}

		MultiPlayerGame g = new MultiPlayerGameImpl(ownerId, playerService.getPlayer(ownerId).getDisplayName());
		mpGames.put(g.getGameId(), g);
		return new ResponseEntity<>(g.getGameId(), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list", produces = "application/json")
	public ResponseEntity<Object> listAll() {
		MPGameListRsp msg = new MPGameListRsp() {
			{
				for (MultiPlayerGame g : mpGames.values()) {
					this.addGame(g.getGameId(), g.listPlayerNames(), g.isStarted());
				}
			}
		};
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/join/{gameId}", produces = "application/json")
	public ResponseEntity<Object> join(@PathVariable UUID gameId, @RequestParam UUID playerId,
			@RequestHeader("jwt") String token) {

		if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}

		MultiPlayerGame g = mpGames.get(gameId);

		if (g.isStarted()) {
			return new ResponseEntity<>("This game is already started", HttpStatus.BAD_REQUEST);
		}

		g.addPlayer(playerId, playerService.getPlayer(playerId).getDisplayName());
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "{gameId}/start", produces = "application/json")
	public ResponseEntity<Object> start(@PathVariable UUID gameId, @RequestParam UUID playerId,
			@RequestHeader("jwt") String token) {
		if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}

		MultiPlayerGame g = mpGames.get(gameId);

		if (g == null) {
			return new ResponseEntity<>("Bad game ID", HttpStatus.BAD_REQUEST);
		}

		if (!playerId.equals(g.getOwnerId())) {
			return new ResponseEntity<>("You are not the owner of this game", HttpStatus.FORBIDDEN);
		}

		g.start();

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "{gameId}/bet", produces = "application/json")
	public ResponseEntity<Object> bet(@PathVariable UUID gameId, @RequestParam UUID playerId, @RequestParam int bet,
			@RequestHeader("jwt") String token) {
		if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}

		MultiPlayerGame g = mpGames.get(gameId);

		if (g == null) {
			return new ResponseEntity<>("Bad game ID", HttpStatus.BAD_REQUEST);
		}

		if (!g.isStarted()) {
			return new ResponseEntity<>("The game is not started yet", HttpStatus.FORBIDDEN);
		}

		if (!playerId.equals(g.getCurrentPlayerId())) {
			return new ResponseEntity<>("Now is not your turn, please wait", HttpStatus.FORBIDDEN);
		}

		try {
			g.setPlayerBet(playerId, bet);
			playerService.updateBalance(playerId, 0 - bet);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}
