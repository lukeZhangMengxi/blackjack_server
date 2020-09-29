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
    public List<Player> getAll() { return playerDAO.getAll();}

    @Transactional
    public long getDeposit(UUID playerId) { return playerDAO.getDeposit(playerId); }

    @Transactional
    public long updateDeposit(UUID playerId, long amount) throws Exception {
        long currentDeposit = playerDAO.getDeposit(playerId);
        if (currentDeposit + amount < 0) {
            throw new Exception("Unsufficient deposit amount");
        }
        playerDAO.updateDeposit(playerId, amount);

        return currentDeposit + amount;
    }
    
}
