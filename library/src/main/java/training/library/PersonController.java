package training.library;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<PersonDto> findPeople(@RequestParam Optional<String> name,@RequestParam Optional<String> date){
        return personService.findPeople(name,date);
    }

    @GetMapping("/find-person/{id}")
    public PersonDto findPersonById(@PathVariable long id){
        return personService.findPersonById(id);
    }

    @PostMapping("/create-new-person")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto createPerson(@Valid CreatePersonCommand person){
        return personService.createPerson(person);
    }

    @PutMapping("/update-warnings/{id}")
    public PersonDto updateWarnings(@PathVariable long id){
        return personService.updateWarnings(id);
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
