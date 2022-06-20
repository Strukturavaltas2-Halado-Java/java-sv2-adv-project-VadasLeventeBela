package training.library;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private ModelMapper modelMapper;
    private BookRepository repository;
    public List<BookDto> findAllBooks(Optional<String> title) {
        List<Book> books = repository.findAll().stream().filter(b-> title.isEmpty()||b.getTitle().equals(title.get()))
                .collect(Collectors.toList());
        Type type = new TypeToken<List<BookDto>>(){}.getType();
        return modelMapper.map(books,type);
    }

    public BookDto findBookById(long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(()->new BookNotFoundException(id)),BookDto.class);
    }

    public BookDto createBook(CreateBookCommand command) {
        return modelMapper.map(repository.save(new Book(command.getTitle(),command.getDescription())),BookDto.class);
    }

    public BookDto updateTimeOfReturn(long id) {
        Book book = repository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        book.setTimeOfReturn(LocalDateTime.now().plusDays(30));
        return modelMapper.map(book,BookDto.class);
    }

    public void removeBookById(long id) {
        repository.deleteById(id);
    }

    public void removeBooks() {
        repository.deleteAll();
    }
}