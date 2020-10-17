package mengxi.blackjack_server.db.dao;

import java.util.List;
import java.util.UUID;

import mengxi.blackjack_server.db.entity.Player;

public interface PlayerDAO {
    List<Player> getAll();

    Player getPlayer(UUID playerId);

    long getDeposit(UUID playerId);

    void updateDeposit(UUID playerId, long amount);
}
