DROP TABLE IF EXISTS player;

CREATE TABLE player (
    id UUID, 
    displayName VARCHAR(25),
    balance BIGINT,
    PRIMARY KEY (id)
);

INSERT INTO player VALUES('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Jack London', '1000');
INSERT INTO player VALUES('c96538ed-e382-4324-bed2-0595dc3213f8', 'Luke Lee', '1000');
