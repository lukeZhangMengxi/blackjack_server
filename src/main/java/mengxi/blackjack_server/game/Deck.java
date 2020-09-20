package mengxi.blackjack_server.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class Deck {
    public static final int NUM_CARDS_IN_A_DECK = 54;

    UUID[][] cards;  // 0, 1-10, JQK JOCKER
    int servedCards;
    Random rand;

    public Deck() {
        cards = new UUID[15][4];
        servedCards = 0;
        rand = new Random();
    }

    public boolean isFull() {
        return servedCards >= NUM_CARDS_IN_A_DECK;
    }

    public boolean serve(final UUID personId) {
        if (this.isFull()) return false;

        int r = rand.nextInt(NUM_CARDS_IN_A_DECK - this.servedCards) + 1;
        int i=1, j=0;
        while (r > 0) {
            if (cards[i][j] == null) {
                if (--r == 0) {
                    cards[i][j] = personId;
                    this.servedCards++;
                    return true;
                }
            }

            if (j < 3) { j++; }
            else if (j == 3) { i++; j=0; }
            else return false;  // Should throw exception
        }

        return false;  // Should throw exception
    }

    public UUID getCardOwnerId(final int i, final int j) {
        if (i<0 || i>=this.cards.length || j<0 || j>=this.cards[0].length) return null;
        return this.cards[i][j];
    }

    @SuppressWarnings("serial")
    public List<String> getCards(final UUID personId) {
        return new ArrayList<String>() {{
            for (int i=0; i<cards.length; i++) {
                for (int j=0; j<cards[0].length; j++) {
                    if (cards[i][j] != null && cards[i][j].equals(personId)) {
                        this.add(i+"#"+j);
                    }
                }
            }
        }};
    }

    public static int cardSum(List<String> cards) {
        // Face cards (J/11,Q/12,K/13) are worth 10. 
        // Aces (A/1) are worth 1 or 11, whichever makes a better hand.
        int sum = 0, numAce = 0, tmp;
        for (String card : cards) {
            tmp = Integer.parseInt(card.split("#")[0]);
            if (tmp >= 10) tmp = 10;
            else if (tmp == 1) {
                tmp = 11; numAce++;
            }

            sum += tmp;
        }
        while (sum > 21 && numAce-- > 0) sum -= 10;
        return sum;
    }
}
