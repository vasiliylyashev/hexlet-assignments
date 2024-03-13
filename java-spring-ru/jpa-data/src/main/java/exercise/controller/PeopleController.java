package exercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Person;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN

    @GetMapping
    public ResponseEntity index() {
        return ResponseEntity.status(HttpStatus.OK).body(personRepository.findAll());
    }

    @PostMapping(consumes = "application/json")
    @ResponseBody
    public ResponseEntity create(@RequestBody String json) {
        ObjectMapper om = new ObjectMapper();
        Person person ;
        try {
            person = (om.readValue(json, Person.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        personRepository.save(person);
        System.out.println(personRepository.findAll());
        return ResponseEntity.status(HttpStatus.CREATED).body(json);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable long id){
        personRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    // END
}
