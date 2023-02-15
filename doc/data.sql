USE weblog;
INSERT INTO blogger(`name`, contact, `password`, email, graduate)
VALUES ('UmiKaiyo', '15000000001', 'password_1', 'test@163.com', 'XX大学'),
       ('HareSora', '15000000002', 'password_2', 'test@163.com', 'XX大学');

INSERT INTO blog(`blogger_id`, visit_count, like_count)
VALUES (1, 0, 0),
       (2, 0, 0);

INSERT INTO tag(`name`, `owner`, `description`)
VALUES ('java', NULL, 'desc-java'),
       ('C', NULL, 'desc-C'),
       ('C++', NULL, 'desc-Cpp'),
       ('Rust', NULL, 'desc-Rust'),
       ('Kotlin', NULL, 'desc-kotlin'),
       ('Haskell', NULL, 'desc-haskell'),
       ('C#', 1, 'C#-desc'),
       ('Go', 1, 'Go-desc'),
       ('docker', 2, 'docker-desc');

INSERT INTO attachment(`name`, suffix, url, `owner`, filesize)
VALUES ('合金压缩包', 'gz', '/attachment/blogger/0/5ZCI6YeR5Y6L57yp5YyFCg.tar.gz', 1, 1024),
       ('纳米压缩包', 'gz', '/attachment/blogger/0/5ZCI6YeR5Y6L57yp5YyFCh.tar.gz', 1, 2048),
       ('量子压缩包', 'gz', '/attachment/blogger/0/5ZCI6YeR5Y6L57yp5YyFCi.tar.gz', 2, 4096);

# 除了Haskell, 都是UmiKaiyo发的博客.
# Java系的语言都不可见(is_public == false), C系的语言都可见.
INSERT INTO post(title, content, detail, author, avatar, is_public, need_review_comment, visits, likes)
VALUES ('Java标题', 'Java简介', 'Java详情', 1, '', FALSE, FALSE, 1, 1),
       ('Kotlin标题', 'Kotlin简介', 'Kotlin详情', 1, '', FALSE, TRUE, 1, 1),
       ('C标题', 'C简介', 'C详情', 1, '', TRUE, TRUE, 1, 1),
       ('C++标题', 'C++简介', 'C++详情', 1, '', TRUE, TRUE, 1, 1),
       ('Rust标题', 'Rust简介', 'Rust详情', 1, '', TRUE, FALSE, 1, 1),
       ('Haskell标题', 'Haskell简介', 'Haskell详情', 2, '', TRUE, FALSE, 1, 1);

INSERT INTO post_tag(post_id, tag_id)
VALUES (1, 1),
       (2, 1),
       (2, 5),
       (3, 2),
       (4, 2),
       (4, 3),
       (5, 2),
       (5, 3),
       (5, 4),
       (6, 6);

INSERT INTO subscribe(publisher, fan)
VALUES (2, 1);

INSERT INTO comment(post_id, user_id, content, reply_to)
VALUES (2, 1, 'dsads', null),
       (1, 3, '1!5!', 1),
       (1, 4, 'LTC!', 3),
       (1, 1, '理塘丁真', null),
       (1, 3, '电子烟！', null),
       (1, 4, '锐克5', null);
