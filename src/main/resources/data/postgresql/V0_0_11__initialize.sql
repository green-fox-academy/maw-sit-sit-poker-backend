CREATE TABLE IF NOT EXISTS Poker_user (
  id       BIGSERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  avatar   VARCHAR(255),
  email    VARCHAR(255) NOT NULL,
  chips    INTEGER DEFAULT 10000
);

CREATE TABLE IF NOT EXISTS game (
  id              BIGSERIAL PRIMARY KEY,
  NAME            VARCHAR(255) NOT NULL,
  big_blind       INTEGER      NOT NULL,
  max_players     INTEGER      NOT NULL
);



