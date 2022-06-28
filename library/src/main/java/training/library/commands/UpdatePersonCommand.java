package training.library.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePersonCommand {
    @Schema(description = "name of person",example = "John Doe")
    @NotBlank
    private String name;

    @Schema(description = "date of birth",example = "2000-01-01")
    @NotNull
    private LocalDate dateOfBirth;
}
