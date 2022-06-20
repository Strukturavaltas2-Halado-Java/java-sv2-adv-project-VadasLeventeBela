package training.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private Person currentHolder;
    private LocalDateTime timeOfReturn;
    private boolean checked;

    public BookDto(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
