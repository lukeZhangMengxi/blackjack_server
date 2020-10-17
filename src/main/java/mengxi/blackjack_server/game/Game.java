package mengxi.blackjack_server.game;

import java.util.List;
import java.util.UUID;

public interface Game {
    public void start(UUID playerId);

    public boolean serveRandomCard(UUID personId);

    public int cardSum(List<String> cards);

    public UUID getGameId();

    public UUID getDelearId();

    public int getPlayerBet();

    public List<String> getDealerCards();

    public List<String> getPlayerCards();

    public int getResult(UUID playerId);

    public void setPlayerBet(int bet) throws Exception;
}
