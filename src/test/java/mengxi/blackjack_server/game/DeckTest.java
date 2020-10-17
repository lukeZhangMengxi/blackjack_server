package mengxi.blackjack_server.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class DeckTest {
    @Test
    public void deckServeAllCards() {
        Deck d = new Deck();
        UUID id = UUID.randomUUID();
        for (int i = 0; i < Deck.NUM_CARDS_IN_A_DECK; i++) {
            assertFalse(d.isFull());
            assertTrue(d.serve(id));
        }
        assertTrue(d.isFull());
    }

    @Test
    public void deckServeCardsInRandomOrder() {
        Deck d1 = new Deck(), d2 = new Deck();
        UUID id;
        for (int i = 0; i < Deck.NUM_CARDS_IN_A_DECK; i++) {
            id = UUID.randomUUID();
            d1.serve(id);
            d2.serve(id);
        }

        int diffCounter = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 4; j++) {
                if (d1.getCardOwnerId(i, j) != null && d2.getCardOwnerId(i, j) != null
                        && !d1.getCardOwnerId(i, j).equals(d2.getCardOwnerId(i, j))) {
                    diffCounter++;
                }
            }
        }

        assertTrue(diffCounter > 0);
    }

    @Test
    public void cardSum() {
        // Regular number sum
        assertEquals(15, Deck.cardSum(
            Arrays.asList("10#1", "5#3")
        ));

        // Ace as 11
        assertEquals(21, Deck.cardSum(
            Arrays.asList("1#1", "10#3")
        ));

        assertEquals(16, Deck.cardSum(
            Arrays.asList("1#1", "5#3")
        ));

        // Ace as 1 when otherwise sum > 21
        assertEquals(12, Deck.cardSum(
            Arrays.asList("1#1", "1#0")
        ));

        assertEquals(17, Deck.cardSum(
            Arrays.asList("1#1", "5#3", "1#0")
        ));

        // J Q K as 10
        assertEquals(20, Deck.cardSum(
            Arrays.asList("11#1", "12#3")
        ));

        assertEquals(12, Deck.cardSum(
            Arrays.asList("13#1", "2#3")
        ));

        // More than 3 cards
        assertEquals(15, Deck.cardSum(
            Arrays.asList("2#1", "3#3", "5#0", "4#1", "1#1")
        ));

        // Eventual sum > 21
        assertEquals(22, Deck.cardSum(
            Arrays.asList("13#1", "2#3", "13#2")
        ));

        // More than one ACE treat as 1
        assertEquals(16, Deck.cardSum(
            Arrays.asList("1#1", "1#3", "2#2", "2#0", "12#3")
        ));

        assertEquals(17, Deck.cardSum(
            Arrays.asList("1#1", "1#3", "2#2", "2#0", "1#0", "12#3")
        ));
    }
}
