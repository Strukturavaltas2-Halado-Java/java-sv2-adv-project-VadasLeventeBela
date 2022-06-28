package training.library.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training.library.services.LibraryService;
import training.library.commands.UpdateLibraryCommand;
import training.library.commands.CreateBookTypeCommand;
import training.library.dtos.LibraryDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/library")
@AllArgsConstructor
@Tag(name = "Operations on library")
public class LibraryController {
    private LibraryService service;

    @GetMapping("/find-all-books")
    @Operation(summary = "Returns every book type.")
    public List<LibraryDto> findAllBooks(@RequestParam Optional<String> title){
        return service.findAllBooks(title);
    }

    @GetMapping("/find-book/{id}")
    @Operation(summary = "Returns book type with the given id.")
    public LibraryDto findBookById(@PathVariable long id){
        return service.findBookById(id);
    }

    @PostMapping("/create-new-book")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adds a new book type to the database.")
    public LibraryDto createNewBook(@Valid @RequestBody CreateBookTypeCommand command){
        return service.createNewBook(command);
    }

    @PutMapping("/rent-new-book")
    @Operation(summary = "Rents a given book to the selected person.")
    public LibraryDto rentBook(@RequestParam long pid,@RequestParam long bid){
        return service.rentBook(pid,bid);
    }

    @PutMapping("/return-book")
    @Operation(summary = "Removes the connection between the person and the book.")
    public LibraryDto returnBook(@RequestParam long pid,@RequestParam long bid){
        return service.returnBook(pid,bid);
    }

    @PutMapping("/update-book-type/{id}")
    @Operation(summary = "Updates the book types information.")
    public LibraryDto updateBookType(@PathVariable long id, @Valid @RequestBody UpdateLibraryCommand command){
        return service.updateBookType(id,command);
    }

    @DeleteMapping("/remove-book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Removes a given book type from the databas")
    public void removeBookById(@PathVariable long id){
        service.removeBookById(id);
    }

    @DeleteMapping("/remove-books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Removes every book type from the database.")
    public void removeBooks(){
        service.removeBooks();
    }


}
