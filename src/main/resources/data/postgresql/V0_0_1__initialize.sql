CREATE TABLE Poker_user (
  id       SERIAL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  avatar   VARCHAR(255),
  email    VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO Poker_user (username, password, email, avatar)
VALUES ('Ramin', '12345', 'example@mail.com', 'http://www.avatar.com');