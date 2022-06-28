package training.library.controllers;

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
    public List<PersonDto> findPeople(@RequestParam Optional<String> name, @RequestParam Optional<String> date){
        return personService.findPeople(name,date);
    }

    @GetMapping("/find-person/{id}")
    public PersonDto findPersonById(@PathVariable long id){
        return personService.findPersonById(id);
    }

    @PostMapping("/create-new-person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto createPerson(@Valid @RequestBody CreatePersonCommand person){
        return personService.createPerson(person);
    }

    @PutMapping("/update-warnings/{id}")
    public PersonDto updateWarnings(@PathVariable long id){
        return personService.updateWarnings(id);
    }

    @PutMapping("/update-person/{id}")
    public PersonDto updatePerson(@PathVariable long id,@Valid @RequestBody UpdatePersonCommand command){
        return personService.updatePerson(id,command);
    }

    @DeleteMapping("/remove-person/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable long id){
        personService.deletePerson(id);
    }

    @DeleteMapping("/remove-people")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePeople(){
        personService.deletePeople();
    }
}
