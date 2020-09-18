package mengxi.blackjack_server.http_msg;

import java.util.List;
import java.util.UUID;


public class GameStartResponse {
    UUID gameId;
    List<String> playerCards;
    List<String> dealerCards;

    public GameStartResponse(UUID gameId, List<String> playerCards, List<String> dealerCards) {
        this.gameId = gameId;
        this.playerCards = playerCards;
        this.dealerCards = dealerCards;
    }
}
