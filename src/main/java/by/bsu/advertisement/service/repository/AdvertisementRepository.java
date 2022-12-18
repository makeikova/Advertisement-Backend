package by.bsu.advertisement.service.repository;

import by.bsu.advertisement.service.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByIsAppear(Boolean isAppear);
    List<Advertisement> findAllByPersonUsername(String username);
}
