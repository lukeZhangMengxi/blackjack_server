package mengxi.blackjack_server.db.dao;

import java.util.List;
import java.util.UUID;

import mengxi.blackjack_server.db.entity.Player;

public interface PlayerDAO {
    List<Player> getAll();

    <T> T getPlayer(UUID playerId, Class<T> type);

    long getBalance(UUID playerId);

    void updateBalance(UUID playerId, long amount);

    UUID createPlayer(String displayName, String email, String passwordHash, String salt);

    UUID authenticate(String email, String passwordRaw);
}
