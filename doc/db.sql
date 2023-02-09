CREATE DATABASE IF NOT EXISTS weblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE weblog;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS blogger;
CREATE TABLE blogger (
    id       BIGINT(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    contact  CHAR(15),
    email    VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    graduate VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,

    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS blog;
CREATE TABLE blog (
    blogger_id  BIGINT(20) UNSIGNED NOT NULL,
    visit_count BIGINT(20) DEFAULT 0,
    like_count  INT(10)    DEFAULT 0
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
    id            BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `owner`       BIGINT(20)          NULL,
    `description` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,

    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS attachment;
CREATE TABLE attachment (
    id       BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `suffix` VARCHAR(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    url      VARCHAR(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    `owner`  BIGINT(20)          NOT NULL,
    filesize BIGINT(20)          NOT NULL,

    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;


SET FOREIGN_KEY_CHECKS = 1;
