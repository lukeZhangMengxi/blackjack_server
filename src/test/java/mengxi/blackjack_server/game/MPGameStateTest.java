package mengxi.blackjack_server.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MPGameStateTest {
    @Test
    public void happyPath() {
        MPGameState state = MPGameState.IDLE;
        // Game start
        state = state.nextState(PlayerAction.IDLE);
        assertEquals(MPGameState.P1_ACT, state);

        // Player 1 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(MPGameState.P2_ACT, state);

        // Player 2 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(MPGameState.P1_ACT, state);

        // Player 1 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(MPGameState.P2_ACT, state);

        // Player 2 stand, player 2 done
        state = state.nextState(PlayerAction.STAND);
        assertEquals(MPGameState.P1_ACT_P2_DONE, state);

        // Player 1 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(MPGameState.P1_ACT_P2_DONE, state);

        // Player 1 hit
        state = state.nextState(PlayerAction.HIT);
        assertEquals(MPGameState.P1_ACT_P2_DONE, state);

        // Player 1 stand, all players done
        state = state.nextState(PlayerAction.STAND);
        assertEquals(MPGameState.DEALER_ACTION, state);

        // Dealer action
        state = state.nextState(PlayerAction.IDLE);
        assertEquals(MPGameState.RESULT, state);
    }
}
