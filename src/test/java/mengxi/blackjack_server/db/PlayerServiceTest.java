package mengxi.blackjack_server.db;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import mengxi.blackjack_server.db.dao.PlayerDAO;
import mengxi.blackjack_server.db.dao.PlayerDAOImpl;
import mengxi.blackjack_server.db.entity.Player;
import mengxi.blackjack_server.db.service.PlayerService;
import mengxi.blackjack_server.db.service.PlayerServiceImpl;
import mengxi.blackjack_server.TestUtils;

@SuppressWarnings("serial")
public class PlayerServiceTest {
    PlayerDAO mockDAO = mock(PlayerDAOImpl.class);
    PlayerService test = new PlayerServiceImpl(mockDAO);
    UUID sampleId = UUID.randomUUID();

    @Test
    public void getAllTest() {
        when(mockDAO.getAll()).thenReturn(null);
        assertEquals(null, test.getAll());

        List<Player> expect = new ArrayList<>() {
            {
                this.add(new Player("b9c8abff-c660-4dba-8ae2-e1456f1e74e2", "name", 123));
                this.add(new Player("9b7cb83f-8e33-439d-ad00-81875fe9a925", "name2", 456));
            }
        };
        when(mockDAO.getAll()).thenReturn(expect);
        TestUtils.assertListEquals(expect, test.getAll());
    }

    @Test
    public void getBalanceTest() {
        when(mockDAO.getBalance(sampleId)).thenReturn(1000l);

        assertEquals(1000, test.getBalance(sampleId));
    }

    @Test
    public void getPlayerTest() {
        Player expect = new Player("b9c8abff-c660-4dba-8ae2-e1456f1e74e2", "name", 123);
        when(mockDAO.getPlayer(sampleId, Player.class)).thenReturn(expect);

        assertEquals(expect, test.getPlayer(sampleId));
    }

    @Test
    public void updateBalanceTest() throws Exception {
        doNothing().when(mockDAO).updateBalance(sampleId, 500);
        when(mockDAO.getBalance(sampleId)).thenReturn(1000l);

        assertEquals(1500, test.updateBalance(sampleId, 500));
    }

    @Test(expected = Exception.class)
    public void updateBalanceTestNegativeBounding() throws Exception {
        doNothing().when(mockDAO).updateBalance(sampleId, -1500);
        when(mockDAO.getBalance(sampleId)).thenReturn(1000l);

        test.updateBalance(sampleId, -1500);
    }

}
