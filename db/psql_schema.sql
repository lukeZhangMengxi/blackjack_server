DROP TABLE IF EXISTS player;

CREATE TABLE player (
    id UUID, 
    displayName VARCHAR(25),
    balance BIGINT,
    email VARCHAR UNIQUE,
    passwordHash VARCHAR,
    salt VARCHAR,
    PRIMARY KEY (id)
);

INSERT INTO player VALUES(
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
    'Jack London',
    '1000',
    'jack@blackjack.com',
    '96f900a313871145e104e8d9e6ff7ed32511b9287828b8117ba6d075e47f09763c547a39233cc4fab64600f79e208a3f1400be3fffe5d57874cf78481b5afc63', /* 'MyPassword' */
    '[B@6fb0d3ed'
);

INSERT INTO player VALUES(
    'c96538ed-e382-4324-bed2-0595dc3213f8',
    'Luke Lee',
    '1000',
    'luke@blackjack.com',
    '3dc3f00161b63444f0cf6f8f7c1c8e1c30491fe1d7a3252d09dfa5d8f68129a08aa340942a3eba1801d41a768e9d5c4954490dfc756d4925c09251950ed99dea', /* 'AnotherPassword' */
    '[B@6fb0d3ed'
);
