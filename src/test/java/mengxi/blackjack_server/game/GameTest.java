package mengxi.blackjack_server.game;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;

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

    @Test
    @SuppressWarnings("unchecked")
    public void getResult() {
        Game g = mock(GameImpl.class);
        UUID someId = UUID.randomUUID();
        doCallRealMethod().when(g).getResult(any(UUID.class));
        doCallRealMethod().when(g).cardSum(any(List.class));
        
        when(g.getPlayerCards()).thenReturn(Arrays.asList("1#2", "5#1"));
        when(g.getDealerCards()).thenReturn(Arrays.asList("1#2", "5#1"));
        assertEquals(0, g.getResult(someId));

        when(g.getPlayerCards()).thenReturn(Arrays.asList("11#2", "5#1"));
        when(g.getDealerCards()).thenReturn(Arrays.asList("5#2", "5#1"));
        assertEquals(1, g.getResult(someId));

        when(g.getPlayerCards()).thenReturn(Arrays.asList("11#2", "5#1", "13#2"));
        when(g.getDealerCards()).thenReturn(Arrays.asList("5#2", "5#1"));
        assertEquals(-1, g.getResult(someId));
    }
}
