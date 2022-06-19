package training.library;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @OneToMany(mappedBy = "person")
    private List<Book> books = new ArrayList<>();

    @NotNull
    @Min(0)
    private int warnings;

    private LocalDateTime suspensionDate;

    public Person(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.warnings=0;
    }

    public void addBook(Book book){
        books.add(book);
    }
}
