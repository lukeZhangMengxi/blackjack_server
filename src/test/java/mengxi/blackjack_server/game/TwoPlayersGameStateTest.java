package mengxi.blackjack_server.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TwoPlayersGameStateTest {
    @Test
    public void happyPath() {
        TwoPlayersGameState state = TwoPlayersGameState.IDLE;
        // Game start
        state = state.nextState(PlayerAction.IDLE);
        assertEquals(TwoPlayersGameState.P1_ACT, state);

        // Player 1 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(TwoPlayersGameState.P2_ACT, state);

        // Player 2 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(TwoPlayersGameState.P1_ACT, state);

        // Player 1 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(TwoPlayersGameState.P2_ACT, state);

        // Player 2 stand, player 2 done
        state = state.nextState(PlayerAction.STAND);
        assertEquals(TwoPlayersGameState.P1_ACT_P2_DONE, state);

        // Player 1 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(TwoPlayersGameState.P1_ACT_P2_DONE, state);

        // Player 1 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(TwoPlayersGameState.P1_ACT_P2_DONE, state);

        // Player 1 stand, all players done
        state = state.nextState(PlayerAction.STAND);
        assertEquals(TwoPlayersGameState.DEALER_ACTION, state);

        // Dealer action
        state = state.nextState(PlayerAction.IDLE);
        assertEquals(TwoPlayersGameState.RESULT, state);
    }
}
