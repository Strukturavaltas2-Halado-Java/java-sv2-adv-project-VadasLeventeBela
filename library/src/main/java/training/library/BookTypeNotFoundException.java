package training.library;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class BookTypeNotFoundException extends AbstractThrowableProblem {
    public BookTypeNotFoundException(long id) {
        super(URI.create("api/library/book-type-not-found"),
                "Not Found",
                Status.NOT_FOUND,
                String.format("Book type with id %d not found!",id));
    }

    public BookTypeNotFoundException() {
        super(URI.create("api/library/book-type-not-found"),
                "Not Found",
                Status.NOT_FOUND,
                "Book type not found!");
    }
}
