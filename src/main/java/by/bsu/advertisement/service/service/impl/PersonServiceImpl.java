package by.bsu.advertisement.service.service.impl;

import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.repository.PersonRepository;
import by.bsu.advertisement.service.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public void create(Person person) {
        person.setStatus("1243");
        personRepository.save(person);
    }
}
