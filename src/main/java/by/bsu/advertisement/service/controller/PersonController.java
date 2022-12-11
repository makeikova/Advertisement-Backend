package by.bsu.advertisement.service.controller;

import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.model.PersonRole;
import by.bsu.advertisement.service.model.request.CreateNewPersonRequest;
import by.bsu.advertisement.service.model.request.CreateNewRoleRequest;
import by.bsu.advertisement.service.model.request.RoleToUserRequest;
import by.bsu.advertisement.service.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class PersonController {

    private final ModelMapper modelMapper;
    private final PersonService personService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Person> getAll(){
        return personService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody CreateNewPersonRequest newUserRequest){
        Person createPerson = modelMapper.map(newUserRequest, Person.class);
        personService.save(createPerson);
    }

    @PostMapping("/role")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRole(@RequestBody CreateNewRoleRequest newRoleRequest){
        PersonRole createRole = modelMapper.map(newRoleRequest, PersonRole.class);
        personService.saveRole(createRole);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void createRole(@RequestBody RoleToUserRequest newRoleRequest){
        personService.addRole(newRoleRequest.getUsername(), newRoleRequest.getName());
    }

}
