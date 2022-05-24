package mengxi.blackjack_server.game.two_players;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TwoPlayersStatefulGameImp implements TwoPlayersStatefulGame {

    @Override
    public UUID getGameId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UUID getCurrentActingPlayerId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UUID getOwnerId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> listPlayerNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAnyPlayersTurn() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isStarted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<String> getDealerCards() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<UUID, List<String>> getPlayersCards() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addPlayer(UUID playerId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setPlayerBet(UUID playerId, int bet) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void playerHit(UUID playerId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void playerStand(UUID playerId) {
        // TODO Auto-generated method stub
        
    }
    
}
