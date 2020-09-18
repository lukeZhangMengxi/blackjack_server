package mengxi.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import mengxi.blackjack_server.game.Deck;

public class DeckTest {
    @Test
    public void deckServeAllCards() {
        Deck d = new Deck();
        UUID id = UUID.randomUUID();
        for (int i=0; i<Deck.NUM_CARDS_IN_A_DECK; i++) {
            assertFalse(d.isFull());
            assertTrue(d.serve(id));
        }
        assertTrue(d.isFull());
    }

    @Test
    public void deckServeCardsInRandomOrder() {
        Deck d1 = new Deck(), d2 = new Deck();
        UUID id;
        for (int i=0; i<Deck.NUM_CARDS_IN_A_DECK; i++) {
            id = UUID.randomUUID();
            d1.serve(id);
            d2.serve(id);
        }
        
        int diffCounter = 0;
        for (int i=0; i<15; i++) {
            for (int j=0; j<4; j++) {
                if (d1.getCardOwnerId(i, j) != null && 
                    d2.getCardOwnerId(i, j) != null &&
                    !d1.getCardOwnerId(i, j).equals(d2.getCardOwnerId(i, j))
                ) {
                    diffCounter++;
                }      
            }
        }

        assertTrue(diffCounter > 0);
    }
}
