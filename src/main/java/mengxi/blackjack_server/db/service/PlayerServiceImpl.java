package mengxi.blackjack_server.db.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mengxi.blackjack_server.db.dao.PlayerDAO;
import mengxi.blackjack_server.db.entity.Player;
import mengxi.blackjack_server.db.entity.PlayerWithCredentials;
import mengxi.blackjack_server.security.HashAPI;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerDAO playerDAO;

    public PlayerServiceImpl(PlayerDAO playerDAO) {
        // For unit test injecting PlayerDAO
        this.playerDAO = playerDAO;
    }

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

    @Transactional
    public UUID createUser(String email, String displayName, String passwordRaw)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] salt = HashAPI.createSalt();
        String passwordHash = HashAPI.encode(passwordRaw, "SHA-512", salt);

        return playerDAO.createPlayer(displayName, email, passwordHash, HashAPI.getSalt(salt));
    }

    @Transactional
    public UUID authenticate(String email, String passwordRaw)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PlayerWithCredentials player = playerDAO.getPlayer(email, PlayerWithCredentials.class);
        byte[] salt = HashAPI.getSalt(player.getSalt());
        String inputPasswordHash = HashAPI.encode(passwordRaw, "SHA-512", salt);

        if (!inputPasswordHash.equals(player.getPasswordHash()))
            return null;

        return player.getId();
    }

}
