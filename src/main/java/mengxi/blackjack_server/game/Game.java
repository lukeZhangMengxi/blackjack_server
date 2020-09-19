package mengxi.blackjack_server.game;

import java.util.List;
import java.util.UUID;


public interface Game {
    public void start(UUID playerId);
    public boolean serveRandomCard(UUID personId);

    public UUID getGameId();
    public List<String> getDealerCards();
    public List<String> getPlayerCards();
}
