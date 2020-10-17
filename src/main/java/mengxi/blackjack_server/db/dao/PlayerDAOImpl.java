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

        template.update(sql, param, new GeneratedKeyHolder());
    }

    @Override
    public Player getPlayer(UUID playerId) {
        final String sql = "select * from player where id = :id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", playerId);

        return template.queryForObject(sql, param, new BeanPropertyRowMapper<Player>(Player.class));
    }

}
