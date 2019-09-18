CREATE TABLE messages (
  id        BIGINT,
  body      VARCHAR(255) NOT NULL,
  createdAt TIMESTAMP,
  sender    varchar(255),
  PRIMARY KEY (id)
)
;
