DROP TABLE IF EXISTS game_table;
DROP TABLE IF EXISTS game;
CREATE TABLE game (
  id SERIAL,
  name VARCHAR(255) NOT NULL,
  big_blind INTEGER NOT NULL,
  max_player_num INTEGER NOT NULL,
  current_players_num INTEGER,
  PRIMARY KEY (id)
);
