package mengxi.blackjack_server.http_msg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MPGameListRsp {

    public class GameInfo {
        UUID id;
        List<String> playerNames;
        boolean started;

        public GameInfo(UUID id, List<String> playerNames, boolean started) {
            this.id = id;
            this.playerNames = new ArrayList<>(playerNames);
            this.started = started;
        }

        public void setId(UUID id) { this.id = id; }
        public UUID getId() { return this.id; }
        public void setPlayerNames(List<String> playerNames) { this.playerNames = playerNames; }
        public List<String> getPlayerNames() { return this.playerNames; }
        public void setStarted(Boolean started) { this.started = started; }
        public boolean getStarted() { return this.started; }
    }

    public List<GameInfo> games;

    public MPGameListRsp() {
        games = new ArrayList<GameInfo>();
    }

    public void addGame(UUID id, List<String> playerNames, boolean started) {
        games.add(new GameInfo(id, playerNames, started));
    }

}
