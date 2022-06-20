CREATE TABLE people
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255) NULL,
    date_of_birth   date NULL,
    warnings        INT NULL,
    suspension_date datetime NULL,
    CONSTRAINT pk_people PRIMARY KEY (id)
);