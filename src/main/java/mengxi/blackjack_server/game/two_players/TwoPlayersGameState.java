package mengxi.blackjack_server.game.two_players;

import mengxi.blackjack_server.game.PlayerAction;

public enum TwoPlayersGameState {

    IDLE {
        @Override
        public TwoPlayersGameState nextState(PlayerAction action) {
            return P1_ACT;
        }
    },

    P1_ACT {
        @Override
        public TwoPlayersGameState nextState(PlayerAction action) {
            switch (action) {
                case HIT: return P2_ACT;
                case STAND: return P2_ACT;
                default: return DEALER_ACTION;
            }
        }
    },

    P1_ACT_P2_DONE {
        @Override
        public TwoPlayersGameState nextState(PlayerAction action) {
            switch (action) {
                case HIT: return P1_ACT_P2_DONE;
                case STAND: return DEALER_ACTION;
                default: return DEALER_ACTION;
            }
        }
    },

    P2_ACT {
        @Override
        public TwoPlayersGameState nextState(PlayerAction action) {
            switch (action) {
                case HIT: return P1_ACT;
                case STAND: return P1_ACT_P2_DONE;
                default: return DEALER_ACTION;
            }
        }
    },

    P2_ACT_P1_DONE {
        @Override
        public TwoPlayersGameState nextState(PlayerAction action) {
            switch (action) {
                case HIT: return P2_ACT_P1_DONE;
                case STAND: return DEALER_ACTION;
                default: return DEALER_ACTION;
            }
        }
    },

    DEALER_ACTION {
        @Override
        public TwoPlayersGameState nextState(PlayerAction action) {
            return RESULT;
        }
    },

    RESULT {
        @Override
        public TwoPlayersGameState nextState(PlayerAction action) {
            return RESULT;
        }
    };

    public abstract TwoPlayersGameState nextState(PlayerAction action);
}
