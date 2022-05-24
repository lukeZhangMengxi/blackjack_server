package mengxi.blackjack_server.websocket_msg;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TwoPlayersStatusMsg {
    public Map<UUID, List<String>> playersCards;
    public List<String> dealerCards;
    public UUID currentActingPlayerId;
}
