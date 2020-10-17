package mengxi.blackjack_server.http_msg;

import java.util.UUID;

public class PlayerRsp {
    public UUID id;
    public String displayName;
    public long deposit;
    public PlayerRsp(UUID playerId, String displayName, long deposit) {
        this.id = playerId;
        this.displayName = displayName;
        this.deposit = deposit;
    }
}
