package mengxi.blackjack_server.game;

import java.util.List;
import java.util.UUID;

public class GameImpl implements Game {

    UUID id, playerId, dealerId;
    Deck deck;

    @Override
    public void start(UUID playerId) {
        id = UUID.randomUUID();
        dealerId = UUID.randomUUID();
        this.playerId = playerId;

        deck = new Deck();
        this.serveRandomCard(playerId, deck);
        this.serveRandomCard(playerId, deck);
        this.serveRandomCard(dealerId, deck);
        this.serveRandomCard(dealerId, deck);
    }

    @Override
    public boolean serveRandomCard(UUID personId, Deck deck) {
        return deck.serve(personId);
    }

    @Override
    public UUID getGameId() {
        return this.id;
    }

    @Override
    public List<String> getDealerCards() {
        return this.deck.getCards(this.dealerId);
    }

    @Override
    public List<String> getPlayerCards() {
        return this.deck.getCards(this.playerId);
    }
    
}
