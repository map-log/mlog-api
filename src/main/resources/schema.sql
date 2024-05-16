CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT,
  email VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  password VARCHAR(256) NOT NULL,
  role VARCHAR(45) NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX email_UNIQUE (email ASC));

CREATE TABLE IF NOT EXISTS travel (
  id BIGINT AUTO_INCREMENT,
  title VARCHAR(300) NOT NULL,
  map_marker_url VARCHAR(300) NOT NULL,
  latitude DECIMAL NOT NULL,
  longitude DECIMAL NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  INDEX user_id_idx (user_id ASC),
  CONSTRAINT user_travel_fk
    FOREIGN KEY (user_id)
    REFERENCES users (id));

CREATE TABLE IF NOT EXISTS travel_detail (
  id BIGINT AUTO_INCREMENT,
  seq INT NOT NULL,
  description TEXT NOT NULL,
  latitude DECIMAL NOT NULL,
  longitude DECIMAL NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME NOT NULL,
  travel_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  INDEX travel_travelDetail_fk_idx (travel_id ASC),
  CONSTRAINT travel_travelDetail_fk
    FOREIGN KEY (travel_id)
    REFERENCES travel (id));

CREATE TABLE IF NOT EXISTS travel_photo (
  id BIGINT AUTO_INCREMENT,
  photo_url VARCHAR(300) NOT NULL,
  created_at DATETIME NOT NULL,
  travel_detail_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  INDEX travelDetail_travelPhoto_idx (travel_detail_id ASC),
  CONSTRAINT travelDetail_travelPhoto
    FOREIGN KEY (travel_detail_id)
    REFERENCES travel_detail (id));