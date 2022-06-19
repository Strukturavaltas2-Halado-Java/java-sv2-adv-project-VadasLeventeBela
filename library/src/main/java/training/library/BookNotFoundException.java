package training.library;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class BookNotFoundException extends AbstractThrowableProblem {
    public BookNotFoundException(long id) {
        super(URI.create("api/books/book-not-found"),
                "Not Found",
                Status.NOT_FOUND,
                String.format("Book with id %d not found!",id));
    }
}
