package training.library;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
@Tag(name = "Operations on Book")
public class BookController {
    private BookService service;

    @GetMapping("/find-all-books")
    public List<BookDto> findAllBooks(@RequestParam Optional<String> title){
        return service.findAllBooks(title);
    }

    @GetMapping("/find-book/{id}")
    public BookDto findBookById(@PathVariable long id){
        return service.findBookById(id);
    }

    @PostMapping("/create-new-book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid CreateBookCommand command){
        return service.createBook(command);
    }

    @PutMapping("/update-time-of-return/{id}")
    public BookDto updateTimeOfReturn(@PathVariable long id){
        return service.updateTimeOfReturn(id);
    }

    @PutMapping("/update-book/{id}")
    public BookDto updateBook(@PathVariable long id,@Valid UpdateBookCommand command){
        return service.updateBook(id,command);
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
