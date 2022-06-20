package training.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @ManyToOne
    private Person person;

    private LocalDateTime timeOfReturn;

    private boolean checked;

    public Book(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
