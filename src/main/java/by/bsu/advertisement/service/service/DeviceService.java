package by.bsu.advertisement.service.service;

import by.bsu.advertisement.service.model.Device;

import java.util.List;

public interface DeviceService {
    List<Device> getAll();
    List<Device> getAllByUserId(Long userId);
    List<Device> getAllByIsActive(Boolean isActive);
    List<Device> getAllByPersonUsername(String username);
    Device getById(Long id);
    void create(Device device);
    void createByUsername(Device device, String username);
    void attachByAdvertisementId(Long deviceId, Long advertisementId);
    void updateById(Long deviceId);
    void toggleStatusById(Long deviceId);
    void deleteForeverById(Long deviceId);
}
