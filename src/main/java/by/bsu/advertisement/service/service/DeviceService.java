package by.bsu.advertisement.service.service;

import by.bsu.advertisement.service.model.Device;

import java.util.List;

public interface DeviceService {
    List<Device> getAll();
    List<Device> getAllByUserId(Long userId);
    Device getById(Long id);
    void create(Device device);
    void createByUserId(Device device, Long userId);
}
