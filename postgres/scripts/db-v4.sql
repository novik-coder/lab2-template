CREATE DATABASE reservations;
GRANT ALL PRIVILEGES ON DATABASE reservations TO program;

CREATE DATABASE libraries;
GRANT ALL PRIVILEGES ON DATABASE libraries TO program;

CREATE DATABASE ratings;
GRANT ALL PRIVILEGES ON DATABASE ratings TO program;
-- 切换到 reservations 数据库
\c reservations;
ALTER SCHEMA public OWNER TO program;

-- 创建 reservations 表
CREATE TABLE reservation
(
    id              SERIAL PRIMARY KEY,
    reservation_uid uuid UNIQUE NOT NULL,
    username        VARCHAR(80) NOT NULL,
    book_uid        uuid        NOT NULL,
    library_uid     uuid        NOT NULL,
    status          VARCHAR(20) NOT NULL
        CHECK (status IN ('RENTED', 'RETURNED', 'EXPIRED')),
    start_date      TIMESTAMP   NOT NULL,
    till_date       TIMESTAMP   NOT NULL
);
ALTER TABLE reservation OWNER TO program;

-- 切换到 libraries 数据库
\c libraries;
ALTER SCHEMA public OWNER TO program;

CREATE TABLE library
(
    id          SERIAL PRIMARY KEY,
    library_uid uuid UNIQUE  NOT NULL,
    name        VARCHAR(80)  NOT NULL,
    city        VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL
);
ALTER TABLE library OWNER TO program;

CREATE TABLE books
(
    id        SERIAL PRIMARY KEY,
    book_uid  uuid UNIQUE  NOT NULL,
    name      VARCHAR(255) NOT NULL,
    author    VARCHAR(255),
    genre     VARCHAR(255),
    condition VARCHAR(20) DEFAULT 'EXCELLENT'
        CHECK (condition IN ('EXCELLENT', 'GOOD', 'BAD'))
);
ALTER TABLE books OWNER TO program;

CREATE TABLE library_books
(
    book_id         INT REFERENCES books (id),
    library_id      INT REFERENCES library (id),
    available_count INT NOT NULL
);
ALTER TABLE library_books OWNER TO program;

INSERT INTO "public"."library" VALUES (1, '83575e12-7ce0-48ee-9931-51919ff3c9ee', 'Библиотека имени 7 Непьющих', 'Москва', '2-я Бауманская ул., д.5, стр.1');
INSERT INTO "public"."library" VALUES (2, '57e29e72-2f53-4a90-8880-9518b7122b50', 'Центральная библиотека', 'Москва', 'Красная площадь, д.10');
INSERT INTO "public"."library" VALUES (3, '3e8a47b0-d5ab-4d1a-8bb5-b973e4f7a1a3', 'Библиотека на Ленинском', 'Москва', 'Ленинский проспект, д.15');
INSERT INTO "public"."library" VALUES (4, 'd2a39f58-2c8d-4edb-a1e2-39b597fd3832', 'Библиотека Новой Волги', 'Саратов', 'ул. Кирова, д.10');
INSERT INTO "public"."library" VALUES (5, '5b28a60d-2c4d-4d16-8fef-ea618238f1b7', 'Северная библиотека', 'Санкт-Петербург', 'Пр. Стачек, д.7');

INSERT INTO "public"."books" VALUES (1, 'f4c2e199-7403-4c5f-a52c-e3fa2b3f4427', 'Война и мир', 'Лев Толстой', 'Исторический роман', 'EXCELLENT');
INSERT INTO "public"."books" VALUES (2, 'd249b07d-d34b-4877-a900-7ab26c9ea1b9', '1984', 'Джордж Оруэлл', 'Фантастика', 'GOOD');
INSERT INTO "public"."books" VALUES (3, 'f7cdc58f-2caf-4b15-9727-f89dcc629b27', 'Краткий курс C++ в 7 томах', 'Бьерн Страуструп', 'Научная фантастика', 'EXCELLENT');
INSERT INTO "public"."books" VALUES (4, 'd2b9d1b5-7c3b-4318-8b1f-dad665c79e3a', 'Война и мир', 'Лев Толстой', 'Классика', 'GOOD');
INSERT INTO "public"."books" VALUES (5, 'ecacb11f-c6e9-4f7e-8d8c-b6f3f6db6edb', '1984', 'Джордж Оруэлл', 'Дистопия', 'EXCELLENT');
INSERT INTO "public"."books" VALUES (6, '8b19c0a4-f8f4-4ab2-b7b2-84f0e33b2f13', 'В поисках утраченного времени', 'Марсель Пруст', 'Роман', 'BAD');
INSERT INTO "public"."books" VALUES (7, '04d0d503-1e72-4171-92b4-f1bde0d6a8ad', 'Мастер и Маргарита', 'Михаил Булгаков', 'Мистика', 'GOOD');

INSERT INTO "public"."library_books" VALUES (1, 1, 5);
INSERT INTO "public"."library_books" VALUES (2, 1, 3);
INSERT INTO "public"."library_books" VALUES (1, 1, 5);
INSERT INTO "public"."library_books" VALUES (2, 1, 3);
INSERT INTO "public"."library_books" VALUES (3, 2, 8);
INSERT INTO "public"."library_books" VALUES (4, 3, 4);
INSERT INTO "public"."library_books" VALUES (5, 4, 2);
INSERT INTO "public"."library_books" VALUES (1, 5, 7);
INSERT INTO "public"."library_books" VALUES (2, 5, 6);
INSERT INTO "public"."library_books" VALUES (3, 1, 3);
-- 切换到 ratings 数据库
\c ratings;
ALTER SCHEMA public OWNER TO program;

-- 创建 ratings 表
CREATE TABLE rating
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(80) NOT NULL,
    stars    INT         NOT NULL
        CHECK (stars BETWEEN 0 AND 100)
);
ALTER TABLE rating OWNER TO program;
