package training.library;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {
    private ModelMapper modelMapper;
    private PersonRepository repository;

    public List<PersonDto> findPeople(Optional<String> name, Optional<String> date) {
        List<Person> people = repository.findAll().stream().filter(p->name.isEmpty()||p.getName().equals(name.get()))
                .filter(p->date.isEmpty()||p.getDateOfBirth().equals(LocalDate.parse(date.get())))
                .collect(Collectors.toList());
        Type type = new TypeToken<List<PersonDto>>(){}.getType();
        return modelMapper.map(people,type);
    }

    public PersonDto findPersonById(long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(()->new PersonNotFoundException(id)),PersonDto.class);
    }

    public PersonDto createPerson(CreatePersonCommand person) {
        return modelMapper.map(repository.save(new Person(person.getName(),person.getDateOfBirth())),PersonDto.class);
    }


    public PersonDto updateWarnings(long id) {
        Person person = repository.findById(id).orElseThrow(()->new PersonNotFoundException(id));
        person.setWarnings(person.getWarnings()+1);
        return modelMapper.map(person,PersonDto.class);
    }

    public PersonDto updatePerson(long id, UpdatePersonCommand command) {
        Person person = repository.findById(id).orElseThrow(()->new PersonNotFoundException(id));
        person.setName(command.getName());
        person.setDateOfBirth(command.getDateOfBirth());
        return modelMapper.map(person,PersonDto.class);
    }

    public void deletePerson(long id) {
        repository.deleteById(id);
    }

    public void deletePeople() {
        repository.deleteAll();
    }
}
