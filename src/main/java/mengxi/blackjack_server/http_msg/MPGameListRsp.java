package mengxi.blackjack_server.http_msg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MPGameListRsp {

    public class GameInfo {
        UUID id;
        List<String> playerNames;

        public GameInfo(UUID id, List<String> playerNames) {
            this.id = id;
            this.playerNames = new ArrayList<>(playerNames);
        }

        public void setId(UUID id) { this.id = id; }
        public UUID getId() { return this.id; }
        public void setPlayerNames(List<String> playerNames) { this.playerNames = playerNames; }
        public List<String> getPlayerNames() { return this.playerNames; }
    }

    public List<GameInfo> games;

    public MPGameListRsp(Map<UUID, List<String>> gameWithPlayerNames) {
        games = new ArrayList<GameInfo>();
        for (UUID id : gameWithPlayerNames.keySet()) {
            games.add(new GameInfo(id, gameWithPlayerNames.get(id)));
        }
    }
}
