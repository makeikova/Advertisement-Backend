package by.bsu.advertisement.service.service.impl;

import by.bsu.advertisement.service.exception.user.UserAlreadyExistsException;
import by.bsu.advertisement.service.model.Advertisement;
import by.bsu.advertisement.service.model.Device;
import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.repository.DeviceRepository;
import by.bsu.advertisement.service.repository.PersonRepository;
import by.bsu.advertisement.service.service.AdvertisementService;
import by.bsu.advertisement.service.service.DeviceService;
import ch.qos.logback.core.property.ResourceExistsPropertyDefiner;
import com.cloudinary.api.exceptions.AlreadyExists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final PersonRepository personRepository;
    private final AdvertisementService advertisementService;

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
    public List<Device> getAllByIsActive(Boolean isActive) {
        return deviceRepository.findAllByIsActive(isActive);
    }

    @Override
    public List<Device> getAllByPersonUsername(String username) {
        return deviceRepository.findAllByPersonUsername(username);
    }

    @Override
    public Device getById(Long id) {
        return deviceRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Device with id: %s not found!", id)));
    }

    @Override
    public void create(Device device) {

    }

    @Override
    public void createByUsername(Device device, String username) {
        Person person = personRepository
                .findByUsername(username);
        if(person == null){
            throw new EntityNotFoundException(String.format("User with username: %s not found!", username));
        }
        device.setPerson(person);
        device.setIsActive(true);
        deviceRepository.save(device);
    }

    @Override
    public void attachByAdvertisementId(Long deviceId, Long advertisementId) {
        Device device = getById(deviceId);
        Advertisement advertisement = advertisementService.getById(advertisementId);
        List<Advertisement> advertisements = device.getAdvertisements();
        for(Advertisement currentAd: advertisements){
            if(currentAd.getId().equals(advertisementId)){
                throw new RuntimeException("Advertisement already attached!");
            }
        }
        device.getAdvertisements().add(advertisement);
        deviceRepository.save(device);
    }

    @Override
    public void updateById(Long deviceId) {
        Device device = getById(deviceId);
    }

    @Override
    public void toggleStatusById(Long deviceId) {
        Device device = getById(deviceId);
        Boolean isActive = device.getIsActive();
        device.setIsActive(!isActive);
        deviceRepository.save(device);
    }

    @Override
    public void deleteForeverById(Long deviceId) {
        getById(deviceId);
        deviceRepository.deleteById(deviceId);
    }
}
