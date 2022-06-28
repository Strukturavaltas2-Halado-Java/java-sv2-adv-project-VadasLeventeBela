package training.library.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import training.library.exceptions.PersonNotFoundException;
import training.library.repos.PersonRepository;
import training.library.commands.UpdateLibraryCommand;
import training.library.commands.CreateBookTypeCommand;
import training.library.dtos.LibraryDto;
import training.library.entities.Book;
import training.library.entities.Library;
import training.library.entities.Person;
import training.library.exceptions.BookNotFoundException;
import training.library.exceptions.BookTypeNotFoundException;
import training.library.repos.BookRepository;
import training.library.repos.LibraryRepository;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LibraryService {
    private ModelMapper modelMapper;
    private LibraryRepository repository;
    private BookRepository bookRepository;
    private PersonRepository personRepository;

    public List<LibraryDto> findAllBooks(Optional<String> title) {
        List<Library> library = repository.findAll().stream()
                .filter(l-> title.isEmpty()||l.getBookTitle().equals(title.get()))
                .collect(Collectors.toList());
        Type type = new TypeToken<List<LibraryDto>>(){}.getType();
        return modelMapper.map(library,type);
    }

    public LibraryDto findBookById(long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(()->new BookTypeNotFoundException(id)),LibraryDto.class);
    }

    public LibraryDto createNewBook(CreateBookTypeCommand command) {
        return modelMapper.map(repository.save(new Library(command.getBookTitle(),command.getAmount())),LibraryDto.class);
    }

    @Transactional
    public LibraryDto rentBook(long pid, long bid) {
        Person person = personRepository.findById(pid).orElseThrow(()->new PersonNotFoundException(pid));
        Book book = bookRepository.findById(bid).orElseThrow(()->new BookNotFoundException(bid));
        Library library = repository.findAll().stream().filter(l->l.getBookTitle().equals(book.getTitle())).findFirst().orElseThrow(BookTypeNotFoundException::new);
        if(validateRent(person,book,library)) {
            book.setCurrentHolder(person);
            book.setTimeOfReturn(LocalDateTime.now().plusDays(30));
            person.addBook(book);
            library.setAmount(library.getAmount() - 1);
        }
        return modelMapper.map(library,LibraryDto.class);
    }

    @Transactional
    public LibraryDto returnBook(long pid, long bid) {
        Person person = personRepository.findById(pid).orElseThrow(()->new PersonNotFoundException(pid));
        Book book = bookRepository.findById(bid).orElseThrow(()->new BookNotFoundException(bid));
        Library library = repository.findAll().stream().filter(l->l.getBookTitle().equals(book.getTitle())).findFirst().orElseThrow(BookTypeNotFoundException::new);
        validatePerson(person);
        book.setCurrentHolder(null);
        book.setTimeOfReturn(null);
        book.setChecked(false);
        person.getBooks().remove(person.getBooks().stream().filter(b->b.getId()==bid).findFirst().orElseThrow(()->new IllegalArgumentException("Can't find book on person!")));
        library.setAmount(library.getAmount()+1);
        return modelMapper.map(library,LibraryDto.class);
    }

    @Transactional
    public LibraryDto updateBookType(long id, UpdateLibraryCommand command) {
        Library library = repository.findById(id).orElseThrow(()->new BookTypeNotFoundException(id));
        library.setBookTitle(command.getBookTitle());
        library.setAmount(command.getAmount());
        return modelMapper.map(library,LibraryDto.class);
    }

    public void removeBookById(long id) {
        repository.deleteById(id);
    }

    public void removeBooks() {
        repository.deleteAll();
    }

    private boolean validateRent(Person person,Book book,Library library){
        validatePerson(person);
        return library.getAmount() != 0 && book.getCurrentHolder() == null && person.getSuspensionDate()==null||!person.getSuspensionDate().isAfter(LocalDateTime.now());
    }

    private void validatePerson(Person person){
        for (Book b :
                person.getBooks()) {
            if (b.getTimeOfReturn().isBefore(LocalDateTime.now())&&!b.isChecked()){
                b.setChecked(true);
                person.setWarnings(person.getWarnings()+1);
            }
        }
        if (person.getWarnings()>=5){
            person.setSuspensionDate(LocalDateTime.now().plusDays(30));
            person.setWarnings(0);
        }
    }
}
