package mengxi.blackjack_server.db.service;

import java.util.List;

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
    
}
