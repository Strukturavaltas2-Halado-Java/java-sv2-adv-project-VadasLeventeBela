package training.library.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonCommand {

    @Schema(description = "name of person",example = "John Doe")
    @NotBlank
    private String name;

    @Schema(description = "date of birth",example = "2000-01-01")
    @NotNull
    private LocalDate dateOfBirth;
}
