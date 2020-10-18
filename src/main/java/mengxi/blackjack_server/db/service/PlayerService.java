package mengxi.blackjack_server.db.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import mengxi.blackjack_server.db.entity.Player;

@Service
public interface PlayerService {
    List<Player> getAll();

    Player getPlayer(UUID playerId);

    long getBalance(UUID playerId);

    long updateBalance(UUID playerId, long amount) throws Exception;

    UUID createUser(String email, String displayName, String passwordRaw)
            throws NoSuchAlgorithmException, UnsupportedEncodingException;

    UUID authenticate(String email, String passwordRaw) throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
