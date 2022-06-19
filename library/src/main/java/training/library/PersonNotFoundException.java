package training.library;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class PersonNotFoundException extends AbstractThrowableProblem {
    public PersonNotFoundException(long id) {
        super(URI.create("api/people/person-not-found"),
                "Not Found",
                Status.NOT_FOUND,
                String.format("Person with id %d not found!",id));
    }
}
