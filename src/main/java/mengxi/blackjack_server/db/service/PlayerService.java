package mengxi.blackjack_server.db.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import mengxi.blackjack_server.db.entity.Player;

@Service
public interface PlayerService {
    List<Player> getAll();
    long getDeposit(UUID playerId);
    long updateDeposit(UUID playerId, long amount) throws Exception;
}
