ALTER TABLE books
    ADD CONSTRAINT FK_BOOKS_ON_PERSON FOREIGN KEY (person_id) REFERENCES people (id);