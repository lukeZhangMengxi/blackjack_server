package mengxi.blackjack_server.http_msg;

import java.util.UUID;

public class PlayerRsp {
    public UUID id;
    public String firstName;
    public String lastName;
    public long deposit;
    public PlayerRsp(UUID playerId, String firstName, String lastName, long deposit) {
        this.id = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deposit = deposit;
    }
}
