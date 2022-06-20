package training.library;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookCommand {

    @Schema(description = "title of the book",example = "Vuk")
    @NotBlank
    private String title;

    @Schema(description = "description of the book",example = "It's a story about ...")
    @NotBlank
    private String description;
}
