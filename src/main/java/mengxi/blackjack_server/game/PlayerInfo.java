package mengxi.blackjack_server.game;

import java.util.UUID;

public class PlayerInfo {
    public int bet;
    public UUID id;
    public String displayName;

    PlayerInfo(UUID id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    PlayerInfo(UUID id, int bet) {
        this.id = id;
        this.bet = bet;
    }
}
