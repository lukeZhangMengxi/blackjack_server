package mengxi.blackjack_server.websocket_msg;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mengxi.blackjack_server.db.entity.Player;
import mengxi.blackjack_server.game.Game;

public class GameStatusMsg {
    public List<PlayerInfo> players;
    public List<String> dealerCards;
    public UUID currentPlayerId;

    class PlayerInfo extends Player {
        List<String> cards;
        int bet;

        PlayerInfo(Player p, List<String> cards, int bet) {
            this.setId(p.getId());
            this.setDisplayName(p.getDisplayName());
            this.setEmail(p.getEmail());
            this.setBalance(p.getBalance());
            this.cards = cards;
            this.bet = bet;
        }

        public List<String> getCards() {
            return cards;
        }
    
        public void setCards(List<String> cards) {
            this.cards = cards;
        }

        public int getBet() {
            return bet;
        }
    
        public void setBet(int bet) {
            this.bet = bet;
        }
    }

    public GameStatusMsg(Game g, Player p, UUID currentPlayerId) {
        this.players = new ArrayList<PlayerInfo>() {
            private static final long serialVersionUID = 1L;
            {
                this.add(new PlayerInfo(p, g.getPlayerCards(), g.getPlayerBet()));
            }
        };
        this.dealerCards = g.getDealerCards();
        this.currentPlayerId = currentPlayerId;
    }
}
