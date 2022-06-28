package training.library;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import training.library.commands.*;
import training.library.dtos.BookDto;
import training.library.dtos.LibraryDto;
import training.library.dtos.PersonDto;
import training.library.entities.Book;
import training.library.entities.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibrariesControllerIT {

    @Autowired
    WebTestClient webTestClient;

    PersonDto personDto;
    BookDto bookDto;
    LibraryDto libraryDto;

    Flyway flyway;

    @BeforeEach
    void init(){
        try {
        MariaDbDataSource dataSource = new MariaDbDataSource("jdbc:mariadb://localhost:3333/libraries");
        dataSource.setUser("libraries");
        dataSource.setPassword("libraries");
        flyway = Flyway.configure().dataSource(dataSource).load();
        }catch (SQLException e){
            throw new IllegalStateException("Can not find data source!");
        }
        flyway.clean();
        flyway.migrate();
        personDto = webTestClient.post()
                .uri("/api/people/create-new-person")
                .bodyValue(new CreatePersonCommand("John Doe", LocalDate.of(2000,01,01)))
                .exchange().expectStatus().isCreated()
                .expectBody(PersonDto.class).returnResult().getResponseBody();
        bookDto = webTestClient.post()
                .uri("/api/books/create-new-book")
                .bodyValue(new CreateBookCommand("Vuk","It's a book"))
                .exchange().expectStatus().isCreated()
                .expectBody(BookDto.class).returnResult().getResponseBody();

        libraryDto = webTestClient.post()
                .uri("/api/library/create-new-book")
                .bodyValue(new CreateBookTypeCommand("Vuk",1))
                .exchange().expectStatus().isCreated()
                .expectBody(LibraryDto.class).returnResult().getResponseBody();
    }


    @Test
    void testCreatePerson(){
        assertThat(personDto).extracting(PersonDto::getName).isEqualTo("John Doe");
    }

    @Test
    void testCreateBook(){
        assertThat(bookDto).extracting(BookDto::getTitle).isEqualTo("Vuk");
    }

    @Test
    void testCreateLibrary(){
        assertThat(libraryDto).extracting(LibraryDto::getAmount).isEqualTo(1);
    }

    @Test
    void testFindPersonById(){
        PersonDto personDto1 = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/people/find-person/{id}").build(personDto.getId()))
                .exchange()
                .expectBody(PersonDto.class).returnResult().getResponseBody();
        assertThat(personDto1).extracting(PersonDto::getName).isEqualTo("John Doe");
    }

    @Test
    void testFindBookById(){
        BookDto bookDto1 = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/books/find-book/{id}").build(bookDto.getId()))
                .exchange()
                .expectBody(BookDto.class).returnResult().getResponseBody();
        assertThat(bookDto1).extracting(BookDto::getTitle).isEqualTo("Vuk");
    }

    @Test
    void testFindBookTypeById(){
        LibraryDto libraryDto1 = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/library/find-book/{id}").build(libraryDto.getId()))
                .exchange()
                .expectBody(LibraryDto.class).returnResult().getResponseBody();
        assertThat(libraryDto1).extracting(LibraryDto::getTitle).isEqualTo("Vuk");
    }

    @Test
    void testFindPeople(){
        List<PersonDto> personDtos = webTestClient.get()
                .uri("/api/people/find-all-people")
                .exchange().expectBodyList(PersonDto.class).returnResult().getResponseBody();
        assertThat(personDtos).hasSize(1).extracting(PersonDto::getName).isEqualTo(List.of("John Doe"));
    }

    @Test
    void testFindBooks(){
        List<BookDto> bookDtos = webTestClient.get()
                .uri("/api/books/find-all-books")
                .exchange().expectBodyList(BookDto.class).returnResult().getResponseBody();
        assertThat(bookDtos).hasSize(1).extracting(BookDto::getTitle).isEqualTo(List.of("Vuk"));
    }

    @Test
    void testFindLibrary(){
        List<LibraryDto> libraryDtos = webTestClient.get()
                .uri("/api/library/find-all-books")
                .exchange().expectBodyList(LibraryDto.class).returnResult().getResponseBody();
        assertThat(libraryDtos).hasSize(1).extracting(LibraryDto::getTitle).isEqualTo(List.of("Vuk"));
    }

    @Test
    void testDeletePerson(){
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/api/people/remove-person/{id}").build(personDto.getId()))
                .exchange().expectStatus().isNoContent();
        List<PersonDto> personDtos = webTestClient.get()
                .uri("/api/people/find-all-people")
                .exchange().expectBodyList(PersonDto.class).returnResult().getResponseBody();
        assertThat(personDtos).isEmpty();
    }

    @Test
    void testDeleteBook(){
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/api/books/remove-book/{id}").build(bookDto.getId()))
                .exchange().expectStatus().isNoContent();
        List<BookDto> bookDtos = webTestClient.get()
                .uri("/api/books/find-all-books")
                .exchange().expectBodyList(BookDto.class).returnResult().getResponseBody();
        assertThat(bookDtos).isEmpty();
    }

    @Test
    void testDeleteBookType(){
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/api/library/remove-book/{id}").build(libraryDto.getId()))
                .exchange().expectStatus().isNoContent();
        List<LibraryDto> libraryDtos = webTestClient.get()
                .uri("/api/library/find-all-books")
                .exchange().expectBodyList(LibraryDto.class).returnResult().getResponseBody();
        assertThat(libraryDtos).isEmpty();
    }

    @Test
    void testDeleteAllPerson(){
        webTestClient.delete()
                .uri("/api/people/remove-people")
                .exchange().expectStatus().isNoContent();
        List<PersonDto> personDtos = webTestClient.get()
                .uri("/api/people/find-all-people")
                .exchange().expectBodyList(PersonDto.class).returnResult().getResponseBody();
        assertThat(personDtos).isEmpty();
    }

    @Test
    void testDeleteAllBook(){
        webTestClient.delete()
                .uri("/api/books/remove-books")
                .exchange().expectStatus().isNoContent();
        List<BookDto> bookDtos = webTestClient.get()
                .uri("/api/books/find-all-books")
                .exchange().expectBodyList(BookDto.class).returnResult().getResponseBody();
        assertThat(bookDtos).isEmpty();
    }

    @Test
    void testDeleteAllBookType(){
        webTestClient.delete()
                .uri("/api/library/remove-books")
                .exchange().expectStatus().isNoContent();
        List<LibraryDto> libraryDtos = webTestClient.get()
                .uri("/api/library/find-all-books")
                .exchange().expectBodyList(LibraryDto.class).returnResult().getResponseBody();
        assertThat(libraryDtos).isEmpty();
    }

    @Test
    void testUpdatePerson(){
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/people/update-person/{id}").build(personDto.getId()))
                .bodyValue(new UpdatePersonCommand("Jane Doe",LocalDate.of(2001,02,02)))
                .exchange();
        List<PersonDto> personDtos = webTestClient.get()
                .uri("/api/people/find-all-people")
                .exchange().expectBodyList(PersonDto.class).returnResult().getResponseBody();
        assertThat(personDtos).hasSize(1).extracting(PersonDto::getName).isEqualTo(List.of("Jane Doe"));
    }

    @Test
    void testUpdateBooks(){
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/books/update-book/{id}").build(bookDto.getId()))
                .bodyValue(new UpdateBookCommand("Vuk 2","desc"))
                .exchange();
        List<BookDto> bookDtos = webTestClient.get()
                .uri("/api/books/find-all-books")
                .exchange().expectBodyList(BookDto.class).returnResult().getResponseBody();
        assertThat(bookDtos).hasSize(1).extracting(BookDto::getTitle).isEqualTo(List.of("Vuk 2"));
    }

    @Test
    void testUpdateLibrary(){
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/library/update-book-type/{id}").build(libraryDto.getId()))
                .bodyValue(new UpdateLibraryCommand("Vuk 2",12))
                .exchange();
        List<LibraryDto> libraryDtos = webTestClient.get()
                .uri("/api/library/find-all-books")
                .exchange().expectBodyList(LibraryDto.class).returnResult().getResponseBody();
        assertThat(libraryDtos).hasSize(1).extracting(LibraryDto::getTitle).isEqualTo(List.of("Vuk 2"));
    }

    @Test
    void testUpdateWarnings(){
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/people/update-warnings/{id}").build(personDto.getId()))
                .exchange();
        List<PersonDto> personDtos = webTestClient.get()
                .uri("/api/people/find-all-people")
                .exchange().expectBodyList(PersonDto.class).returnResult().getResponseBody();
        assertThat(personDtos).hasSize(1).extracting(PersonDto::getWarnings).isEqualTo(List.of(1));
    }

    @Test
    void testUpdateReturnDate(){
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/books/update-time-of-return/{id}").build(bookDto.getId()))
                .bodyValue(new UpdateBookCommand("Vuk 2","desc"))
                .exchange();
        List<BookDto> bookDtos = webTestClient.get()
                .uri("/api/books/find-all-books")
                .exchange().expectBodyList(BookDto.class).returnResult().getResponseBody();
        assertThat(bookDtos.get(0)).extracting(BookDto::getTimeOfReturn).isNotNull();
    }

    @Test
    void testRentAndReturnBook(){
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/library/rent-new-book").queryParam("pid",personDto.getId()).queryParam("bid",bookDto.getId()).build())
                .exchange();
        PersonDto personDto1 = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/people/find-person/{id}").build(personDto.getId()))
                .exchange()
                .expectBody(PersonDto.class).returnResult().getResponseBody();
        BookDto bookDto1 = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/books/find-book/{id}").build(bookDto.getId()))
                .exchange()
                .expectBody(BookDto.class).returnResult().getResponseBody();
        assertThat(personDto1.getBooks().get(0)).extracting(Book::getTitle).isEqualTo("Vuk");
        assertThat(bookDto1.getCurrentHolder()).extracting(Person::getName).isEqualTo("John Doe");
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/library/return-book").queryParam("pid",personDto.getId()).queryParam("bid",bookDto.getId()).build())
                .exchange();
        personDto1 = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/people/find-person/{id}").build(personDto.getId()))
                .exchange()
                .expectBody(PersonDto.class).returnResult().getResponseBody();
        bookDto1 = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/books/find-book/{id}").build(bookDto.getId()))
                .exchange()
                .expectBody(BookDto.class).returnResult().getResponseBody();
        assertThat(personDto1).extracting(PersonDto::getBooks).isEqualTo(List.of());
        assertThat(bookDto1).extracting(BookDto::getCurrentHolder).isNull();
    }
}
