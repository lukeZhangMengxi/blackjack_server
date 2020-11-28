package mengxi.blackjack_server.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mengxi.blackjack_server.db.service.PlayerService;
import mengxi.blackjack_server.game.Game;
import mengxi.blackjack_server.game.GameImpl;
import mengxi.blackjack_server.http_msg.ResultResponse;
import mengxi.blackjack_server.http_msg.StatusResponse;
import mengxi.blackjack_server.security.JwtAPI;
import mengxi.blackjack_server.security.JwtAPI.ClaimType;
import mengxi.blackjack_server.websocket_msg.GameStatusMsg;

@RestController
@RequestMapping("game")
public class GameController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SimpMessagingTemplate broker;

    private Map<UUID, Game> games = new HashMap<UUID, Game>();

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @RequestMapping(method = RequestMethod.GET, value = "/{gameId}/status", produces = "application/json")
    public ResponseEntity<Object> status(@PathVariable UUID gameId, @RequestParam UUID playerId,
            @RequestHeader("jwt") String token) throws JsonProcessingException {

        if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        if (games.containsKey(gameId)) {
            Game g = games.get(gameId);
            StatusResponse msg = new StatusResponse(g.getPlayerCards(), g.getDealerCards(), g.getPlayerBet(),
                    playerService.getBalance(playerId));

            // Publish the game status
            broker.convertAndSend("/topic/game/" + g.getGameId().toString(),
                    new GameStatusMsg(g, playerService.getPlayer(playerId), playerId));

            return new ResponseEntity<>(mapper.writeValueAsString(msg), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @PostMapping("/start")
    public ResponseEntity<Object> start(@RequestParam UUID playerId, @RequestHeader("jwt") String token) {
        if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        Game g = new GameImpl();
        g.start(playerId);
        games.put(g.getGameId(), g);
        return new ResponseEntity<>(g.getGameId(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @PostMapping("/{gameId}/bet")
    public ResponseEntity<Object> bet(@RequestParam UUID playerId, @RequestParam Integer bet, @PathVariable UUID gameId,
            @RequestHeader("jwt") String token) {

        if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        if (games.containsKey(gameId)) {
            Game g = games.get(gameId);
            try {
                g.setPlayerBet(bet);
                playerService.updateBalance(playerId, 0 - bet);

                // Publish the game status
                broker.convertAndSend("/topic/game/" + g.getGameId().toString(),
                        new GameStatusMsg(g, playerService.getPlayer(playerId), playerId));

                return new ResponseEntity<>(null, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @PostMapping("/{gameId}/hit")
    public ResponseEntity<Object> hit(@RequestParam UUID playerId, @PathVariable UUID gameId,
            @RequestHeader("jwt") String token) {

        if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        if (games.containsKey(gameId)) {
            Game g = games.get(gameId);
            g.serveRandomCard(playerId);

            // Publish the game status
            broker.convertAndSend("/topic/game/" + g.getGameId().toString(),
                    new GameStatusMsg(g, playerService.getPlayer(playerId), playerId));

            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @PostMapping("/{gameId}/stand")
    public ResponseEntity<Object> stand(@RequestParam UUID playerId, @PathVariable UUID gameId,
            @RequestHeader("jwt") String token) {

        if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        if (games.containsKey(gameId)) {
            Game g = games.get(gameId);
            while (g.cardSum(g.getDealerCards()) < 17) {
                g.serveRandomCard(g.getDelearId());
            }

            // Publish the game status
            broker.convertAndSend("/topic/game/" + g.getGameId().toString(),
                    new GameStatusMsg(g, playerService.getPlayer(playerId), playerId));

            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @RequestMapping(method = RequestMethod.GET, value = "/{gameId}/result", produces = "application/json")
    public ResponseEntity<Object> close(@RequestParam UUID playerId, @PathVariable UUID gameId,
            @RequestHeader("jwt") String token) {

        if (!JwtAPI.verifyToken(token, playerId.toString(), ClaimType.PLAYERID)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        if (games.containsKey(gameId)) {
            Game g = games.get(gameId);
            int result = g.getResult(playerId);

            try {
                long newBalance = -1;
                // If win, return double bet
                if (result == 1)
                    newBalance = playerService.updateBalance(playerId, 2 * g.getPlayerBet());
                // If tied, return bet
                else if (result == 0)
                    newBalance = playerService.updateBalance(playerId, g.getPlayerBet());
                // else, return 0
                else
                    newBalance = playerService.getBalance(playerId);

                // Publish the game status
                broker.convertAndSend("/topic/game/" + g.getGameId().toString(),
                        new GameStatusMsg(g, playerService.getPlayer(playerId), playerId));

                ResultResponse msg = new ResultResponse(result, newBalance);
                return new ResponseEntity<>(mapper.writeValueAsString(msg), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
