package training.library.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training.library.entities.Person;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @JsonBackReference
    private Person currentHolder;
    private LocalDateTime timeOfReturn;
    private boolean checked;

    public BookDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
