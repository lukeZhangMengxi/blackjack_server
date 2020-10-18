package mengxi.blackjack_server.db.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mengxi.blackjack_server.db.dao.PlayerDAO;
import mengxi.blackjack_server.db.entity.Player;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    @Transactional
    public List<Player> getAll() {
        return playerDAO.getAll();
    }

    @Transactional
    public long getBalance(UUID playerId) {
        return playerDAO.getBalance(playerId);
    }

    @Transactional
    public Player getPlayer(UUID playerId) {
        return playerDAO.getPlayer(playerId, Player.class);
    }

    @Transactional
    public long updateBalance(UUID playerId, long amount) throws Exception {
        long currentBalance = playerDAO.getBalance(playerId);
        if (currentBalance + amount < 0) {
            throw new Exception("Unsufficient balance amount");
        }
        playerDAO.updateBalance(playerId, amount);

        return currentBalance + amount;
    }

}
