package mengxi.blackjack_server.controller;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mengxi.blackjack_server.db.entity.Player;
import mengxi.blackjack_server.db.service.PlayerService;
import mengxi.blackjack_server.http_msg.LoginRsp;
import mengxi.blackjack_server.http_msg.PlayerRsp;
import mengxi.blackjack_server.security.JwtAPI;
import mengxi.blackjack_server.security.JwtAPI.ClaimType;

@RestController
@RequestMapping("player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();


    @GetMapping("/all")
	public List<Player> all() {
		return playerService.getAll();
    }
    
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@RequestMapping(method = RequestMethod.GET, value = "/{playerId}", produces = "application/json")
	public ResponseEntity<Object> player(@PathVariable UUID playerId, @RequestHeader("jwt") String token)
			throws JsonProcessingException {
		if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}

		Player p = playerService.getPlayer(playerId);
		if (p == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		PlayerRsp msg = new PlayerRsp(p.getId(), p.getDisplayName(), p.getBalance());
		return new ResponseEntity<>(mapper.writeValueAsString(msg), HttpStatus.OK);

	}

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@RequestMapping(method = RequestMethod.POST, value = "/create", produces = "application/json")
	public ResponseEntity<Object> createPlayer(@RequestParam String email, @RequestParam String password,
			@RequestParam String displayName) {

		if (playerService.getPlayer(email) != null) {
			return new ResponseEntity<>("Email already registered", HttpStatus.BAD_REQUEST);
		}

		try {
			playerService.createUser(email, displayName, password);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(500));
		}

		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
	@RequestMapping(method = RequestMethod.POST, value = "/login", produces = "application/json")
	public ResponseEntity<Object> loginPlayer(@RequestParam String email, @RequestParam String password) {

		try {
			UUID playerId = playerService.authenticate(email, password);
			if (playerId == null) {
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			} else {
				String jwtToken = JwtAPI.generateToken(email, playerId.toString(), 30);
				LoginRsp msg = new LoginRsp(playerId, jwtToken);
				return new ResponseEntity<>(mapper.writeValueAsString(msg), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.resolve(500));
		}
	}
}
