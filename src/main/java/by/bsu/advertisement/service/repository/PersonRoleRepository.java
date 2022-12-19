package by.bsu.advertisement.service.repository;

import by.bsu.advertisement.service.model.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRole, Long> {
    PersonRole findByName(String name);
}
