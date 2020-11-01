package mengxi.blackjack_server.http_msg;

import java.util.UUID;

public class LoginRsp {
    public UUID playerId;
    public String token;

    public LoginRsp(UUID playerId, String token) {
        this.playerId = playerId;
        this.token = token;
    }
}
