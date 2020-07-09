package mengxi.blackjack_server.db.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mengxi.blackjack_server.db.entity.Player;

@Service
public interface PlayerService {
    List<Player> getAll();
}
