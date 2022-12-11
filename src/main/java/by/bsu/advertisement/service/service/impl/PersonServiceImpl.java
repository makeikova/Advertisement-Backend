package by.bsu.advertisement.service.service.impl;

import by.bsu.advertisement.service.exception.user.UserAlreadyExistsException;
import by.bsu.advertisement.service.exception.user.UserNotFoundException;
import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.model.PersonRole;
import by.bsu.advertisement.service.repository.PersonRepository;
import by.bsu.advertisement.service.repository.PersonRoleRepository;
import by.bsu.advertisement.service.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService, UserDetailsService {

    private final PersonRepository personRepository;
    private final PersonRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(Person person) {
        log.info("New saved user to database: {}", person.getUsername());

        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);

        Person savedPerson = personRepository.save(person);
        String newPersonUsername = savedPerson.getUsername();

        addRole(newPersonUsername, "ROLE_USER");
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);

        if(person == null) {
            log.error("User with username: {} not found!", username);
            throw new UserNotFoundException(String.format("User with username: %s not found!", username));
        }else {
            log.info("User with username: {} was found!", username);
        }

        String personUsername = person.getUsername();
        String personPassword = person.getPassword();
        Collection<SimpleGrantedAuthority> personAuthorities = new ArrayList<>();

        person.getRoles().forEach(
                role -> personAuthorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        return new User(personUsername, personPassword, personAuthorities);
    }
}
