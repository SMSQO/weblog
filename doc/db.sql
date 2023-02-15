CREATE DATABASE IF NOT EXISTS weblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE weblog;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS blogger;
CREATE TABLE blogger
(
    id         BIGINT(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    contact    CHAR(15) UNIQUE,
    email      VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    graduate   VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
    `password` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,

    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS blog;
CREATE TABLE blog
(
    blogger_id  BIGINT(20) UNSIGNED NOT NULL,
    visit_count BIGINT(20) UNSIGNED DEFAULT 0,
    like_count  INT(10)             DEFAULT 0
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS tag;
CREATE TABLE tag
(
    id            BIGINT(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    `owner`       BIGINT(20) UNSIGNED                                           NULL,
    `description` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,

    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS attachment;
CREATE TABLE attachment
(
    id       BIGINT(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    `suffix` VARCHAR(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    url      VARCHAR(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `owner`  BIGINT(20) UNSIGNED                                           NOT NULL,
    filesize BIGINT(20) UNSIGNED                                           NOT NULL,

    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS post;
CREATE TABLE post
(
    id                  BIGINT(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT,
    title               VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    content             VARCHAR(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    detail              MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL,

    author              BIGINT(20) UNSIGNED                                           NOT NULL,
    avatar              VARCHAR(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,

    is_public           TINYINT(1)                                                    NOT NULL,
    need_review_comment TINYINT(1)                                                    NOT NULL,
    visits              INT(10) UNSIGNED                                              NOT NULL DEFAULT 0,
    likes               INT(10) UNSIGNED                                              NOT NULL DEFAULT 0,

    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS post_tag;
CREATE TABLE post_tag
(
    post_id BIGINT(20) UNSIGNED NOT NULL,
    tag_id  BIGINT(20) UNSIGNED NOT NULL,

    PRIMARY KEY (post_id, tag_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS subscribe;
CREATE TABLE subscribe
(
    publisher BIGINT(20) UNSIGNED NOT NULL,
    fan       BIGINT(20) UNSIGNED NOT NULL,

    PRIMARY KEY (publisher, fan),
    INDEX i_publisher (publisher),
    INDEX i_fan (fan)
);

DROP TABLE IF EXISTS comment;
CREATE TABLE comment
(
    id            BIGINT(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT,
    post_id       BIGINT(20) UNSIGNED                                           NOT NULL,
    user_id       BIGINT(20) UNSIGNED                                           NOT NULL,
    content       VARCHAR(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    reply_to      BIGINT(20) UNSIGNED,
    review_passed BOOL,
    PRIMARY KEY (id),
    INDEX i_blogId (post_id),
    INDEX i_replyTo (reply_to)
);

SET FOREIGN_KEY_CHECKS = 1;
