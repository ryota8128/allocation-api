CREATE SCHEMA IF NOT EXISTS ALLOCATION;
SET SCHEMA ALLOCATION;

CREATE TABLE USER(
    ID BIGINT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL
);

CREATE TABLE ACCOUNT (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `num_free_transfer` int NOT NULL DEFAULT '0',
  `transfer_fee` int NOT NULL DEFAULT '100',
  `owner_id` bigint NOT NULL,
  `via` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name_ownerId` (`name`,`owner_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS TRANSFER (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(20) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);


CREATE TABLE regular_transfer (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  from_account BIGINT,
  to_account BIGINT,
  description TEXT NOT NULL,
  percentage BOOLEAN NOT NULL DEFAULT FALSE,
  amount INT UNSIGNED,
  ratio FLOAT,
  user_id BIGINT,
  FOREIGN KEY (from_account) REFERENCES account(id),
  FOREIGN KEY (to_account) REFERENCES account(id),
  FOREIGN KEY (user_id) REFERENCES user(id)
);


CREATE TABLE temporary_transfer (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  from_account BIGINT,
  to_account BIGINT,
  description TEXT NOT NULL,
  amount INT UNSIGNED NOT NULL,
  user_id BIGINT,
  transfer_id BIGINT,
  FOREIGN KEY (from_account) REFERENCES account(id),
  FOREIGN KEY (to_account) REFERENCES account(id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (transfer_id) REFERENCES transfer(id)
);
