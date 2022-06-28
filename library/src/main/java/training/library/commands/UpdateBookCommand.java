package training.library.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookCommand {

    @Schema(description = "title of the book",example = "Vuk")
    @NotBlank
    private String title;

    @Schema(description = "description of the book",example = "It's a story about ...")
    @NotBlank
    private String description;
}
