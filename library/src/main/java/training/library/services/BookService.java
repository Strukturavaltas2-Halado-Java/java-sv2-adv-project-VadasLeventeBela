package training.library.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import training.library.commands.CreateBookCommand;
import training.library.commands.UpdateBookCommand;
import training.library.dtos.BookDto;
import training.library.entities.Book;
import training.library.entities.Library;
import training.library.exceptions.BookNotFoundException;
import training.library.repos.BookRepository;
import training.library.repos.LibraryRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private ModelMapper modelMapper;
    private BookRepository repository;
    private LibraryRepository libraryRepository;
    public List<BookDto> findAllBooks(Optional<String> title) {
        List<Book> books = repository.findAll().stream().filter(b-> title.isEmpty()||b.getTitle().equals(title.get()))
                .collect(Collectors.toList());
        Type type = new TypeToken<List<BookDto>>(){}.getType();
        return modelMapper.map(books,type);
    }

    public BookDto findBookById(long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(()->new BookNotFoundException(id)),BookDto.class);
    }
    @Transactional
    public BookDto createBook(CreateBookCommand command) {
        checkLibrary(command);
        return modelMapper.map(repository.save(new Book(command.getTitle(),command.getDescription())),BookDto.class);
    }

    @Transactional
    public BookDto updateTimeOfReturn(long id,String dateTime) {
        Book book = repository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        book.setTimeOfReturn(LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return modelMapper.map(book,BookDto.class);
    }

    @Transactional
    public BookDto updateBook(long id, UpdateBookCommand command) {
        Book book = repository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        book.setTitle(command.getTitle());
        book.setDescription(command.getDescription());
        return modelMapper.map(book,BookDto.class);
    }

    public void removeBookById(long id) {
        repository.deleteById(id);
    }

    public void removeBooks() {
        repository.deleteAll();
    }

    private void checkLibrary(CreateBookCommand command){
        Optional<Library> libraryChecker = libraryRepository.findAll().stream().filter(library -> library.getBookTitle().equals(command.getTitle())).findFirst();
        if (libraryChecker.isEmpty()){
            libraryRepository.save(new Library(command.getTitle(),1));
        }else {
            libraryChecker.get().setAmount(libraryChecker.get().getAmount()+1);
        }
    }
}
