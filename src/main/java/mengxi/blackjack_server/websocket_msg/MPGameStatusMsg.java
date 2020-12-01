package mengxi.blackjack_server.websocket_msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MPGameStatusMsg {

    public Map<UUID, PlayerInfo> players;
    public List<String> dealerCards;
    public UUID currentPlayerId;
    public boolean finished;

    public void addPlayer(UUID id, String name, List<String> cards, int bet) {
        if (players == null) {
            players = new HashMap<>();
        }
        if (players.containsKey(id)) {
            return;
        }

        players.put(id, new PlayerInfo(cards, name, bet));
    }

    public void setDealerCards(List<String> cards) {
        this.dealerCards = cards;
    }

    public void setCurrentPlayerId(UUID id) {
        this.currentPlayerId = id;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    class PlayerInfo {

        String name;
        List<String> cards;
        int bet;

        PlayerInfo(List<String> cards, String name, int bet) {
            this.cards = new ArrayList<>(cards);
            this.name = name;
            this.bet = bet;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getCards() {
            return cards;
        }

        public void setCards(List<String> cards) {
            this.cards = new ArrayList<>(cards);
        }

        public int getBet() {
            return bet;
        }

        public void setBet(int bet) {
            this.bet = bet;
        }
    }
}
