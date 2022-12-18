package by.bsu.advertisement.service;

import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.model.PersonRole;
import by.bsu.advertisement.service.repository.PersonRepository;
import by.bsu.advertisement.service.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class ServiceApplication {

	private final PersonService personService;
	private final PersonRepository personRepository;

	public ServiceApplication(PersonService personService, PersonRepository personRepository) {
		this.personService = personService;
		this.personRepository = personRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			personService.saveRole(new PersonRole(null, "ROLE_ADMIN"));
			personService.saveRole(new PersonRole(null, "ROLE_USER"));
			Person admin = new Person(null, "admin", "admin@admin.com", "admin", false, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
			Person user = new Person(null, "user", "user@user.com", "1234", false, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
			personService.save(admin);
			personService.save(user);
			personService.addRole("admin", "ROLE_ADMIN");
		};
	}
}
