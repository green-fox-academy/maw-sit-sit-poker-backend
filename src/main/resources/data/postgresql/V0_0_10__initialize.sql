DROP TABLE IF EXISTS game;
CREATE TABLE game (
  id              SERIAL,
  name            VARCHAR(255) NOT NULL,
  big_blind       INTEGER      NOT NULL,
  max_players     INTEGER      NOT NULL,
  current_players INTEGER,
  PRIMARY KEY (id)
);