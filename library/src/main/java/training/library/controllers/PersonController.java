package training.library.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training.library.services.PersonService;
import training.library.commands.UpdatePersonCommand;
import training.library.commands.CreatePersonCommand;
import training.library.dtos.PersonDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/people")
@Tag(name = "Operations on person")
public class PersonController {

    private PersonService personService;

    @GetMapping("/find-all-people")
    @Operation(summary = "Returns every person sorted by the parameters.")
    public List<PersonDto> findPeople(@RequestParam Optional<String> name, @RequestParam Optional<String> date){
        return personService.findPeople(name,date);
    }

    @GetMapping("/find-person/{id}")
    @Operation(summary = "Returns the person with the given id.")
    public PersonDto findPersonById(@PathVariable long id){
        return personService.findPersonById(id);
    }

    @PostMapping("/create-new-person")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new person.")
    public PersonDto createPerson(@Valid @RequestBody CreatePersonCommand person){
        return personService.createPerson(person);
    }

    @PutMapping("/update-warnings/{id}")
    @Operation(summary = "Increases the warnings of the given person by 1.")
    public PersonDto updateWarnings(@PathVariable long id){
        return personService.updateWarnings(id);
    }

    @PutMapping("/update-person/{id}")
    @Operation(summary = "Updates the selected person with given information.")
    public PersonDto updatePerson(@PathVariable long id,@Valid @RequestBody UpdatePersonCommand command){
        return personService.updatePerson(id,command);
    }

    @DeleteMapping("/remove-person/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Removes the selected person.")
    public void deletePerson(@PathVariable long id){
        personService.deletePerson(id);
    }

    @DeleteMapping("/remove-people")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Removes every person.")
    public void deletePeople(){
        personService.deletePeople();
    }
}
