package mengxi.blackjack_server.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import mengxi.blackjack_server.TestUtils;

public class MultiPlayerGameTest {
    @Test
    public void simpleGameWorkflow() throws Exception {
        UUID ownerId = UUID.randomUUID();
        MultiPlayerGameImpl g = new MultiPlayerGameImpl(ownerId, "");

        UUID player2Id = UUID.randomUUID();
        UUID player3Id = UUID.randomUUID();
        g.addPlayer(player2Id, "");
        g.addPlayer(player3Id, "");
        
        // After players join the game, there should be 3 players, each having no poker card
        assertFalse(g.started);
        assertEquals(3, g.players.size());
        for (UUID id : g.players.keySet()) {
            assertTrue(g.getPlayerCards(id).isEmpty());
        }


        g.start();

        // After game started, every player should receive 2 unique cards
        assertTrue(g.started);
        List<String> buf = new ArrayList<>(g.getDealerCards());
        for (UUID id : g.players.keySet()) {
            List<String> cards = g.getPlayerCards(id);

            assertEquals(2, cards.size());
            TestUtils.assertUniqueCards(cards, buf);

            buf.addAll(cards);
        }


        UUID curPlayerId = g.getCurrentPlayerId();

        // First player's turn
        assertNotNull(curPlayerId);

        g.setPlayerBet(curPlayerId, 100);
        assertEquals(100, g.getPlayerBet(curPlayerId));

        g.serveRandomCard(curPlayerId);
        assertEquals(3, g.getPlayerCards(curPlayerId).size());


        // Next player's turn
        assertFalse(g.allPlayerFinished());

        g.nextPlayer();
        assertNotEquals(curPlayerId, g.getCurrentPlayerId());

        curPlayerId = g.getCurrentPlayerId();
        g.setPlayerBet(curPlayerId, 500);
        assertEquals(500, g.getPlayerBet(curPlayerId));

        g.serveRandomCard(curPlayerId);
        g.serveRandomCard(curPlayerId);
        g.serveRandomCard(curPlayerId);
        assertEquals(5, g.getPlayerCards(curPlayerId).size());


        // Next player's turn
        assertFalse(g.allPlayerFinished());

        g.nextPlayer();
        assertNotEquals(curPlayerId, g.getCurrentPlayerId());

        curPlayerId = g.getCurrentPlayerId();
        assertEquals(2, g.getPlayerCards(curPlayerId).size());


        // All players finished their turns
        assertTrue(g.allPlayerFinished());
    }
}