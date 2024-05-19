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
    title_img_url VARCHAR(300) NOT NULL,
    description TEXT NOT NULL,
    rating DECIMAL NOT NULL,
    lat VARCHAR(300) NOT NULL,
    lng VARCHAR(300) NOT NULL,
    start_at DATETIME NOT NULL,
    end_at DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS travel_detail (
    id BIGINT AUTO_INCREMENT,
    seq INT NOT NULL,
    title VARCHAR(300) NOT NULL,
    description TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    travel_id BIGINT NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS travel_photo (
    id BIGINT AUTO_INCREMENT,
    photo_url VARCHAR(300) NOT NULL,
    created_at DATETIME NOT NULL,
    travel_detail_id BIGINT NOT NULL,
    PRIMARY KEY (id));