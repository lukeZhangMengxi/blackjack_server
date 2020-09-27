package mengxi.blackjack_server.http_msg;

import java.util.List;

public class StatusResponse {
    public List<String> playerCards;
    public List<String> dealerCards;
    public int playerBet;
    public StatusResponse(int bet, List<String> pCards, List<String> dCards) {
        this.playerBet = bet;
        this.playerCards = pCards;
        this.dealerCards = dCards;
    }
}
