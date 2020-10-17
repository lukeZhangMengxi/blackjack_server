package mengxi.blackjack_server.http_msg;

import java.util.List;

public class StatusResponse {
    public List<String> playerCards;
    public List<String> dealerCards;
    public int playerBet;
    public long playerDeposit;

    public StatusResponse(List<String> pCards, List<String> dCards, int bet, long deposit) {
        this.playerCards = pCards;
        this.dealerCards = dCards;
        this.playerBet = bet;
        this.playerDeposit = deposit;
    }
}
