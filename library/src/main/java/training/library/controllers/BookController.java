package training.library.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training.library.services.BookService;
import training.library.commands.CreateBookCommand;
import training.library.commands.UpdateBookCommand;
import training.library.dtos.BookDto;
import training.library.services.LibraryService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
@Tag(name = "Operations on Book")
public class BookController {
    private BookService service;

    @GetMapping("/find-all-books")
    @Operation(summary = "Returns every book in the database.")
    public List<BookDto> findAllBooks(@RequestParam Optional<String> title){
        return service.findAllBooks(title);
    }

    @GetMapping("/find-book/{id}")
    @Operation(summary = "Returns the selected book from the database.")
    public BookDto findBookById(@PathVariable long id){
        return service.findBookById(id);
    }

    @PostMapping("/create-new-book")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adds a new book to the database.")
    public BookDto createBook(@Valid @RequestBody CreateBookCommand command){
        return service.createBook(command);
    }

    @PutMapping("/update-time-of-return/{id}")
    @Operation(summary = "Updates the return time of the selected book.")
    public BookDto updateTimeOfReturn(@PathVariable long id, @RequestParam String dateTime){
        return service.updateTimeOfReturn(id,dateTime);
    }

    @PutMapping("/update-book/{id}")
    @Operation(summary = "Update the information of the selected book.")
    public BookDto updateBook(@PathVariable long id,@Valid @RequestBody UpdateBookCommand command){
        return service.updateBook(id,command);
    }

    @DeleteMapping("/remove-book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Removes the selected book.")
    public void removeBookById(@PathVariable long id){
        service.removeBookById(id);
    }

    @DeleteMapping("/remove-books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Removes every book from the database.")
    public void removeBooks(){
        service.removeBooks();
    }

}
