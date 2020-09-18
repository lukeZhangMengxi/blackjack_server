package mengxi.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import mengxi.blackjack_server.game.Game;
import mengxi.blackjack_server.game.GameImpl;

public class GameTest {

    @SuppressWarnings("serial")
    void assertUnique(List<String> a, List<String> b) {
        Set<String> s = new HashSet<>() {{
            for (String s : a) this.add(s);
            for (String s : b) this.add(s);
        }};

        assertEquals(a.size() + b.size(), s.size());
    }

    @Test
    public void gameStart() {
        Game g = new GameImpl();
        UUID id = UUID.randomUUID();
        g.start(id);

        List<String> playerCards = g.getPlayerCards();
        List<String> dealerCards = g.getDealerCards();

        assertEquals(2, playerCards.size());
        assertEquals(2, dealerCards.size());
        assertUnique(playerCards, dealerCards);
    }
}
