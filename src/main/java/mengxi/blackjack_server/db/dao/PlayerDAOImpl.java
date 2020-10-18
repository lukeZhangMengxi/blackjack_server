package mengxi.blackjack_server.db.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import mengxi.blackjack_server.db.entity.Player;

@Repository
public class PlayerDAOImpl implements PlayerDAO {

    private NamedParameterJdbcTemplate template;

    private static final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

    public PlayerDAOImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Player> getAll() {
        final String sql = new StringBuilder().append("select * from player").toString();

        return (List<Player>) template.query(sql, new BeanPropertyRowMapper<Player>(Player.class));
    }

    @Override
    public long getBalance(UUID playerId) {
        final String sql = "select * from player where id = :id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", playerId);

        Player p = template.queryForObject(sql, param, new BeanPropertyRowMapper<Player>(Player.class));
        return p.getBalance();
    }

    @Override
    public void updateBalance(UUID playerId, long amount) {
        final String sql = "update player set balance = balance + :amount where id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", playerId).addValue("amount", amount);

        template.update(sql, param, keyHolder);
    }

    @Override
    public <T> T getPlayer(UUID playerId, Class<T> type) {
        final String sql = "select * from player where id = :id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", playerId);

        return template.queryForObject(sql, param, new BeanPropertyRowMapper<T>(type));
    }

    @Override
    public UUID createPlayer(String displayName, String email, String passwordHash, String salt) {
        final String sql = "INSERT INTO player VALUES(" + ":id," + ":displayName," + "0," + ":email," + ":passwordHash,"
                + ":salt" + ")";

        UUID newId = UUID.randomUUID();
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", newId)
                .addValue("displayName", displayName).addValue("email", email).addValue("passwordHash", passwordHash)
                .addValue("salt", salt);

        template.update(sql, param, keyHolder);

        return newId;
    }

    @Override
    public UUID authenticate(String email, String passwordRaw) {
        // TODO Auto-generated method stub
        return null;
    }

}
