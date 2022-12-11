package by.bsu.advertisement.service.controller;

import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.model.request.CreateNewPersonRequest;
import by.bsu.advertisement.service.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class PersonController {

    private final ModelMapper modelMapper;
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateNewPersonRequest newUserRequest){
        Person createPerson = modelMapper.map(newUserRequest, Person.class);
        personService.create(createPerson);
    }

}
