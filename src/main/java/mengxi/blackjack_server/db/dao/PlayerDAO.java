package mengxi.blackjack_server.db.dao;

import java.util.List;
import java.util.UUID;

import mengxi.blackjack_server.db.entity.Player;

public interface PlayerDAO {
    List<Player> getAll();

    Player getPlayer(UUID playerId);

    long getBalance(UUID playerId);

    void updateBalance(UUID playerId, long amount);

    UUID createPlayer(String displayName, String email, String passwordHash, String salt);
}
