package by.bsu.advertisement.service.repository;

import by.bsu.advertisement.service.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByPersonId(Long userId);
}
