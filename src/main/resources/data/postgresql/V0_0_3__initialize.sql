CREATE TABLE game_player (
  id       SERIAL,
  username VARCHAR(255) NOT NULL,
  email    VARCHAR(255) NOT NULL,
  avatar   VARCHAR(255),
  chips INTEGER,
  PRIMARY KEY(id)
);
