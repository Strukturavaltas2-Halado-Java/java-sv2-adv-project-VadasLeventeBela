package training.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String bookTitle;

    @NotBlank
    @Min(0)
    private int amount;

    public Library(String bookTitle, int amount) {
        this.bookTitle = bookTitle;
        this.amount = amount;
    }
}
