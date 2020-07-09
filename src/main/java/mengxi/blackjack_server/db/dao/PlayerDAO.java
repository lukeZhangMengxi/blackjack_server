package mengxi.blackjack_server.db.dao;

import java.util.List;

import mengxi.blackjack_server.db.entity.Player;


public interface PlayerDAO {
    List<Player> getAll();
}
