package mengxi.blackjack_server.db.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
        final String sql = new StringBuilder()
                        .append("select * from player")
                        .toString();

        return (List<Player>)template.query(sql, new BeanPropertyRowMapper<Player>(Player.class));
    }

}
