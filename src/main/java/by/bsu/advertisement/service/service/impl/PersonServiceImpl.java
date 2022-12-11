package by.bsu.advertisement.service.service.impl;

import by.bsu.advertisement.service.exception.user.UserAlreadyExistsException;
import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.model.PersonRole;
import by.bsu.advertisement.service.repository.PersonRepository;
import by.bsu.advertisement.service.repository.PersonRoleRepository;
import by.bsu.advertisement.service.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonRoleRepository roleRepository;

    @Override
    public void save(Person person) {
        log.info("New saved user to database: {}", person.getUsername());
        personRepository.save(person);
    }

    @Override
    public PersonRole saveRole(PersonRole personRole) {
        log.info("New saved role to database: {}", personRole.getName());
        return roleRepository.save(personRole);
    }

    @Override
    public void addRole(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        Person person = personRepository.findByUsername(username);
        PersonRole role = roleRepository.findByName(roleName);

        person.getRoles().add(role);

        personRepository.save(person);
    }

    @Override
    public Person findByUsername(String username) {
        log.info("Fetching user {}", username);
        return personRepository.findByUsername(username);
    }

    @Override
    public List<Person> getAll() {
        log.info("Fetching all users");
        return personRepository.findAll();
    }
}
