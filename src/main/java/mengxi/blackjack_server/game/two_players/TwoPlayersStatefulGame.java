package mengxi.blackjack_server.game.two_players;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface TwoPlayersStatefulGame {
    public UUID getGameId();
    public UUID getCurrentActingPlayerId();
    public UUID getOwnerId();
    public List<String> listPlayerNames();
    public boolean isAnyPlayersTurn();
    public boolean isStarted();
    public List<String> getDealerCards();
    public Map<UUID, List<String>> getPlayersCards();

    public void addPlayer(UUID playerId);
    public void start();
    public void setPlayerBet(UUID playerId, int bet) throws Exception;
    public void playerHit(UUID playerId);
    public void playerStand(UUID playerId);
}
