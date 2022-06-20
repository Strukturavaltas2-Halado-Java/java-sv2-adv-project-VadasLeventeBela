package training.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLibraryCommand {
    @Schema(description = "title of the book",example = "Vuk")
    @NotBlank
    private String bookTitle;

    @Schema(description = "the amount of books on hand",example = "12")
    @NotNull
    @Min(0)
    private int amount;
}
