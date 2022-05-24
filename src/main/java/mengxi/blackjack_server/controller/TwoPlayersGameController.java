package mengxi.blackjack_server.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mengxi.blackjack_server.db.service.PlayerService;
import mengxi.blackjack_server.game.MultiPlayerGame;
import mengxi.blackjack_server.game.two_players.TwoPlayersStatefulGame;
import mengxi.blackjack_server.game.two_players.TwoPlayersStatefulGameImp;
import mengxi.blackjack_server.http_msg.MPGameListRsp;
import mengxi.blackjack_server.security.JwtAPI;
import mengxi.blackjack_server.security.JwtAPI.ClaimType;
import mengxi.blackjack_server.websocket_msg.MPGameStatusMsg;

@RestController
@RequestMapping("2p-game")
public class TwoPlayersGameController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private SimpMessagingTemplate broker;

    private Map<UUID, TwoPlayersStatefulGame> twoPGames = new HashMap<UUID, TwoPlayersStatefulGame>();

    private void broadcastGameListStatus() {
        broker.convertAndSend("/topic/gameListStatus", new MPGameListRsp() {
            {
                for (TwoPlayersStatefulGame g : twoPGames.values()) {
                    this.addGame(g.getGameId(), g.listPlayerNames(), g.isStarted());
                }
            }
        });
    }

    private void broadcast(TwoPlayersStatefulGame g) {
        // Publish the game status
        broker.convertAndSend("/topic/game/" + g.getGameId().toString(), new MPGameStatusMsg() {
            {
                this.setCurrentPlayerId(g.getCurrentActingPlayerId());
                this.setDealerCards(g.getDealerCards());
                // this.setFinished(g.allPlayerFinished());
            }
        });
    }

    private void computeResultForEachPlayer(MultiPlayerGame g) {
        for (UUID playerId : g.getPlayers().keySet()) {
            try {
                if (g.computeResult(playerId) == 1) {
                    playerService.updateBalance(playerId, 2 * g.getPlayerBet(playerId));
                } else if (g.computeResult(playerId) == 0) {
                    playerService.updateBalance(playerId, g.getPlayerBet(playerId));
                }
            } catch (Exception e) {
                // Log error, should not stop the process
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create", produces = "application/json")
    public ResponseEntity<Object> create(@RequestParam UUID ownerId, @RequestHeader("jwt") String token) {
        if (!JwtAPI.verifyToken(token, ownerId.toString(), ClaimType.PLAYERID)) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        TwoPlayersStatefulGame g = new TwoPlayersStatefulGameImp();
        twoPGames.put(g.getGameId(), g);
        broadcastGameListStatus();
        return new ResponseEntity<>(g.getGameId(), HttpStatus.CREATED);
    }

}
