package training.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
