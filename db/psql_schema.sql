DROP TABLE IF EXISTS player;

CREATE TABLE player (
    id UUID, 
    firstName VARCHAR(25),
    lastName VARCHAR(25),
    PRIMARY KEY (id)
);

INSERT INTO player VALUES('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Jack', 'London');
INSERT INTO player VALUES('c96538ed-e382-4324-bed2-0595dc3213f8', 'Luke', 'Lee');