create schema if not exists myshema;
DROP TABLE IF EXISTS goods;
DROP TABLE IF EXISTS checks CASCADE;
DROP TABLE IF EXISTS checklines CASCADE;
DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS check_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE if not exists myshema."goods"
(
    id     INT8 PRIMARY KEY DEFAULT nextval('global_seq'),
    ean    INT8    NOT NULL,
    "name" VARCHAR NOT NULL,
    price  FLOAT   NOT NULL
);
CREATE UNIQUE INDEX if not exists goods_unique_idx ON myshema."goods" (ean);

CREATE SEQUENCE check_seq START WITH 1;
CREATE TABLE if not exists myshema."checks"
(
    id       INT8 PRIMARY KEY DEFAULT nextval('global_seq'),
    check_id INT8             DEFAULT nextval('check_seq'),
    "date"   DATE             DEFAULT now() NOT NULL,
    "time"   TIME             DEFAULT now() NOT NULL,
    total    FLOAT                          NOT NULL
);
CREATE UNIQUE INDEX if not exists checks_unique_idx ON myshema."checks" (check_id);

CREATE TABLE if not exists myshema."checklines"
(
    line_id   INT8 PRIMARY KEY DEFAULT nextval('global_seq'),
    check_id  INT8  NOT NULL,
    goods_ean INT8  NOT NULL,
    count     INT   NOT NULL,
    total     FLOAT NOT NULL,
    FOREIGN KEY (check_id) REFERENCES myshema."checks" (check_id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX if not exists checklines_unique_idx ON myshema."checks" (check_id);