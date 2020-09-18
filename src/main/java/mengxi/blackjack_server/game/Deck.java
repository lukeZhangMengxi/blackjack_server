package mengxi.blackjack_server.game;

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

    public boolean serve(UUID personId) {
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

    public UUID getCardOwnerId(int i, int j) {
        if (i<0 || i>=this.cards.length || j<0 || j>=this.cards[0].length) return null;
        return this.cards[i][j];
    }
}
