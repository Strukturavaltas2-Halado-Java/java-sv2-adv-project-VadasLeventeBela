package training.library;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/library")
@AllArgsConstructor
public class LibraryController {
    private LibraryService service;

    @GetMapping("/find-all-books")
    public List<LibraryDto> findAllBooks(@RequestParam Optional<String> title){
        return service.findAllBooks(title);
    }

    @GetMapping("/find-book/{id}")
    public LibraryDto findBookById(@PathVariable long id){
        return service.findBookById(id);
    }

    @PostMapping("/create-new-book")
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryDto createNewBook(@Valid @RequestBody CreateBookTypeCommand command){
        return service.createNewBook(command);
    }

    @PutMapping("/rent-new-book")
    public LibraryDto rentBook(@RequestParam long pid,@RequestParam long bid){
        return service.rentBook(pid,bid);
    }

    @PutMapping("/return-book")
    public LibraryDto returnBook(@RequestParam long pid,@RequestParam long bid){
        return service.returnBook(pid,bid);
    }

    @PutMapping("/update-book-type/{id}")
    public LibraryDto updateBookType(@PathVariable long id, @Valid @RequestBody UpdateLibraryCommand command){
        return service.updateBookType(id,command);
    }

    @DeleteMapping("/remove-book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBookById(@PathVariable long id){
        service.removeBookById(id);
    }

    @DeleteMapping("/remove-books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBooks(){
        service.removeBooks();
    }


}
