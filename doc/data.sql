USE weblog;
INSERT INTO blogger(`name`, contact, email, graduate)
VALUES ('UmiKaiyo', '15001234567', 'test@163.com', 'XX大学'),
       ('HareSora', '15001234567', 'test@163.com', 'XX大学');

INSERT INTO blog(`blogger_id`, visit_count, like_count)
VALUES (1, 0, 0),
       (1, 0, 0);

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

INSERT INTO attachment(`name`, url, `owner`, filesize)
VALUES ('合金压缩包', '/attachment/blogger/0/5ZCI6YeR5Y6L57yp5YyFCg.tar.gz', 1, 1024),
       ('纳米压缩包', '/attachment/blogger/0/5ZCI6YeR5Y6L57yp5YyFCh.tar.gz', 1, 2048),
       ('量子压缩包', '/attachment/blogger/0/5ZCI6YeR5Y6L57yp5YyFCi.tar.gz', 2, 4096);