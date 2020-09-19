package mengxi.blackjack_server.http_msg;

import java.util.List;
import java.util.UUID;

public class GameStartResponse {

    private UUID gameId;
    private List<String> playerCards;
    private List<String> dealerCards;

    public GameStartResponse(UUID gameId, List<String> playerCards, List<String> dealerCards) {
        this.gameId = gameId;
        this.playerCards = playerCards;
        this.dealerCards = dealerCards;
    }

    UUID getGameId() { return this.gameId; }
    List<String> getPlayerCards() { return this.playerCards; }
    List<String> getDealerCards() { return this.playerCards; }

    void setGameId(UUID gameId) { this.gameId = gameId; }
    void setPlayerCards(List<String> playerCards) { this.playerCards = playerCards; }
    void setDealerCards(List<String> dealerCards) { this.dealerCards = dealerCards; }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder("GameStartResponse{");
        buf.append("id:\"").append(gameId).append("\"");
        buf.append(", playerCards:").append(playerCards);
        buf.append(", dealerCards:").append(dealerCards);
        buf.append('}');
        return buf.toString();
    }
}
