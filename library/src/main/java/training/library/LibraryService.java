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

    public LibraryDto rentBook(long pid, long bid) {
        Person person = personRepository.findById(pid).orElseThrow(()->new PersonNotFoundException(pid));
        Book book = bookRepository.findById(bid).orElseThrow(()->new BookNotFoundException(bid));
        Library library = repository.findAll().stream().filter(l->l.getBookTitle().equals(book.getTitle())).findFirst().orElseThrow(BookTypeNotFoundException::new);
        if(validateRent(person,book,library)) {
            person.addBook(book);
            book.setPerson(person);
            book.setTimeOfReturn(LocalDateTime.now().plusDays(30));
            library.setAmount(library.getAmount() - 1);
        }
        return modelMapper.map(library,LibraryDto.class);
    }

    public LibraryDto returnBook(long pid, long bid) {
        Person person = personRepository.findById(pid).orElseThrow(()->new PersonNotFoundException(pid));
        Book book = bookRepository.findById(bid).orElseThrow(()->new BookNotFoundException(bid));
        Library library = repository.findAll().stream().filter(l->l.getBookTitle().equals(book.getTitle())).findFirst().orElseThrow(BookTypeNotFoundException::new);
        validatePerson(person);
        book.setPerson(null);
        book.setTimeOfReturn(null);
        book.setChecked(false);
        person.getBooks().remove(person.getBooks().stream().filter(b->b.getId()==bid).findFirst().orElseThrow(()->new IllegalArgumentException("Can't find book on person!")));
        library.setAmount(library.getAmount()+1);
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
        return library.getAmount() != 0 && book.getPerson() == null && !person.getSuspensionDate().isAfter(LocalDateTime.now());
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
