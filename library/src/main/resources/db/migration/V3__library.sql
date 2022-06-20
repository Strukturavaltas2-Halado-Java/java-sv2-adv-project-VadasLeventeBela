CREATE TABLE library
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    book_title VARCHAR(255) NULL,
    amount     INT NULL,
    CONSTRAINT pk_library PRIMARY KEY (id)
);