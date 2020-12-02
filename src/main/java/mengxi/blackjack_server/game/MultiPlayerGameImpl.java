package mengxi.blackjack_server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MultiPlayerGameImpl implements MultiPlayerGame {

    UUID id, dealerId, currentPlayerId, ownerId;
    Deck deck;
    Map<UUID, PlayerInfo> players;
    Set<UUID> finishedPlayerIds;
    boolean started;

    public MultiPlayerGameImpl(UUID ownerId, String ownerName) {
        this.id = UUID.randomUUID();
        this.dealerId = UUID.randomUUID();
        this.deck = new Deck();
        this.started = false;

        this.ownerId = ownerId;
        this.players = new HashMap<>();
        this.players.put(ownerId, new PlayerInfo(ownerId, ownerName));
        this.finishedPlayerIds = new HashSet<>();
    }

    @Override
    public void addPlayer(UUID playerId, String playerName) {
        players.put(playerId, new PlayerInfo(playerId, playerName));
    }

    @Override
    public void start() {
        int count = 0;
        while (count++ < INIT_CARD_NUMBER) {
            for (PlayerInfo p : players.values()) {
                serveRandomCard(p.id);
            }
            serveRandomCard(dealerId);
        }

        started = true;
        try {
            nextPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean serveRandomCard(UUID playerId) {
        return deck.serve(playerId);
    }

    @Override
    public UUID getGameId() {
        return id;
    }

    @Override
    public int getPlayerBet(UUID playerId) {
        return players.get(playerId).bet;
    }

    @Override
    public List<String> getDealerCards() {
        return deck.getCards(dealerId);
    }

    @Override
    public List<String> getPlayerCards(UUID playerId) {
        return deck.getCards(playerId);
    }

    @Override
    public int getResult(UUID playerId) {
        int dealerPoints = Deck.cardSum(getDealerCards());
        int playerPoints = Deck.cardSum(getPlayerCards(playerId));
        int rst = 0;
        if (playerPoints > 21) {
            rst = -1;
        } else if (playerPoints == dealerPoints) {
            rst = 0;
        } else {
            if (dealerPoints > 21)
                rst = 1;
            else
                rst = (playerPoints > dealerPoints) ? 1 : -1;
        }
        return rst;
    }

    @Override
    public void setPlayerBet(UUID playerId, int bet) throws Exception {
        if (bet < 0)
            throw new Exception("Bet can not be negative.");
        players.get(playerId).bet = bet;
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public UUID getCurrentPlayerId() {
        return currentPlayerId;
    }

    @Override
    public void nextPlayer() throws Exception {
        if (this.currentPlayerId != null) {
            finishedPlayerIds.add(this.currentPlayerId);
        }

        if (players.size() == finishedPlayerIds.size()) {
            return;
        }

        int counter = (int) (Math.random() * (players.size() - finishedPlayerIds.size()));
        for (UUID id : players.keySet()) {
            if (finishedPlayerIds.contains(id)) {
                continue;
            } else {
                if (counter-- == 0) {
                    this.currentPlayerId = id;
                    return;
                }
            }
        }

        // Should not reach here
        throw new Exception("Error looking for next player");
    }

    @Override
    public boolean allPlayerFinished() {
        return finishedPlayerIds.size() == players.size();
    }

    @Override
    public List<String> listPlayerNames() {
        return new ArrayList<String>() {
            private static final long serialVersionUID = 1L;
            {
                for (PlayerInfo p : players.values()) {
                    this.add(p.displayName);
                }
            }
        };
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public void dealerAction() {
        while (Deck.cardSum(this.getDealerCards()) < 17) {
            this.serveRandomCard(this.dealerId);
        }
    }

    @Override
    public Map<UUID, PlayerInfo> getPlayers() {
        return this.players;
    }

    @Override
    public int computeResult(UUID playerId) {
        int dealerPoints = Deck.cardSum(this.getDealerCards());
        int playerPoints = Deck.cardSum(this.getPlayerCards(playerId));
        int rst = 0;
        if (playerPoints > 21) {
            rst = -1;
        } else if (playerPoints == dealerPoints) {
            rst = 0;
        } else {
            if (dealerPoints > 21)
                rst = 1;
            else
                rst = (playerPoints > dealerPoints) ? 1 : -1;
        }
        return rst;
    }

}
