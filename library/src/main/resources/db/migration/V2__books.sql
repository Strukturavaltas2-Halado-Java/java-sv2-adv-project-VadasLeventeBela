CREATE TABLE books
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    title          VARCHAR(255) NULL,
    `description`  VARCHAR(255) NULL,
    person_id      BIGINT NULL,
    time_of_return datetime NULL,
    checked        BIT(1) NULL,
    CONSTRAINT pk_books PRIMARY KEY (id)
);