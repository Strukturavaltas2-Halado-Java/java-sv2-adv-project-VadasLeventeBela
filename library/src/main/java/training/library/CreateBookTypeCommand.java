package training.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookTypeCommand {
    @Schema(description = "title of the book",example = "Vuk")
    @NotBlank
    private String bookTitle;

    @Schema(description = "the amount of books on hand",example = "12")
    @NotNull
    @Min(0)
    private int amount;
}
