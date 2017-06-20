CREATE TABLE IF NOT EXISTS Poker_user (
  id       SERIAL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  avatar   VARCHAR(255),
  email    VARCHAR(255) NOT NULL,
  chips    INTEGER DEFAULT 10000,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS game_player (
  id       SERIAL,
  username VARCHAR(255) NOT NULL,
  email    VARCHAR(255) NOT NULL,
  avatar   VARCHAR(255),
  chips INTEGER,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS game_table (
  id SERIAL,
  name VARCHAR(255) NOT NULL,
  big_blind INTEGER NOT NULL,
  max_player_num INTEGER NOT NULL,
  current_players_num INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS game (
  id              SERIAL,
  name            VARCHAR(255) NOT NULL,
  big_blind       INTEGER      NOT NULL,
  max_players     INTEGER      NOT NULL,
  current_players INTEGER,
  PRIMARY KEY (id)
);