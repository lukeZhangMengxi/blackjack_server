package mengxi.blackjack_server.game;

import java.util.List;
import java.util.UUID;

public interface MultiPlayerGame {

    public final static int INIT_CARD_NUMBER = 2;
    public final static int MAX_PLAYER_NUMBER = 4;

    public UUID getOwnerId();

    public UUID getCurrentPlayerId();

    public void nextPlayer() throws Exception;

    public boolean allPlayerFinished();

    public void addPlayer(UUID playerId, String playerName);

    public void start();

    public boolean isStarted();

    public boolean serveRandomCard(UUID playerId);

    public UUID getGameId();

    public int getPlayerBet(UUID playerId);

    public List<String> getDealerCards();

    public List<String> getPlayerCards(UUID playerId);

    public int getResult(UUID playerId);

    public void setPlayerBet(UUID playerId, int bet) throws Exception;

    public List<String> listPlayerNames();

    public void dealerAction();
}
