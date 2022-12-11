package by.bsu.advertisement.service.service.impl;

import by.bsu.advertisement.service.model.Device;
import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.repository.DeviceRepository;
import by.bsu.advertisement.service.repository.PersonRepository;
import by.bsu.advertisement.service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final PersonRepository personRepository;

    @Override
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> getAllByUserId(Long userId) {
        Person person = personRepository
                .findById(userId)
                .orElseThrow();
        return deviceRepository.findAllByPersonId(userId);
    }

    @Override
    public Device getById(Long id) {
        return deviceRepository
                .findById(id)
                .orElseThrow();
    }

    @Override
    public void create(Device device) {

    }

    @Override
    public void createByUserId(Device device, Long userId) {
        Person person = personRepository
                .findById(userId)
                .orElseThrow();
        device.setPerson(person);

        deviceRepository.save(device);
    }
}
