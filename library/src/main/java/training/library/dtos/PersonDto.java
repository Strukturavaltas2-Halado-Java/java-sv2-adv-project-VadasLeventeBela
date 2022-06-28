package training.library.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training.library.entities.Book;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private long id;
    @NotBlank
    private String name;

    @NotNull
    private LocalDate dateOfBirth;
    @JsonBackReference
    private List<Book> books = new ArrayList<>();

    @NotNull
    @Min(0)
    private int warnings;

    private LocalDateTime suspensionDate;

    public PersonDto(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.warnings=0;
    }
}
