package by.bsu.advertisement.service.service;

import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.model.PersonRole;

import java.util.List;

public interface PersonService {
    void save(Person person);
    PersonRole saveRole(PersonRole personRole);
    void addRole(String username, String roleName);
    Person findByUsername(String username);
    List<Person> getAll();
    void toggleBlockStatusById(Long userId);
}
