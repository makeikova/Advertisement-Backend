package by.bsu.advertisement.service.controller;

import by.bsu.advertisement.service.model.Device;
import by.bsu.advertisement.service.model.dto.DeviceDto;
import by.bsu.advertisement.service.model.request.CreateNewDeviceRequest;
import by.bsu.advertisement.service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/device")
@RequiredArgsConstructor
public class DeviceController {

    private final ModelMapper modelMapper;
    private final DeviceService deviceService;

    @GetMapping
    public List<DeviceDto> getAll(@RequestParam Boolean isActive){
        List<Device> devices = deviceService.getAllByIsActive(isActive);
        return devices.stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("all")
    public List<DeviceDto> getAllWithout(){
        List<Device> devices = deviceService.getAll();
        return devices.stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("my/{username}")
    public List<DeviceDto> getAllByUsername(@PathVariable String username){
        List<Device> devices = deviceService.getAllByPersonUsername(username);
        return devices.stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{deviceId}")
    public DeviceDto getById(@PathVariable Long deviceId){
        Device device = deviceService.getById(deviceId);

        return modelMapper.map(device, DeviceDto.class);
    }

    @PostMapping("{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateNewDeviceRequest newDeviceRequest,
                       @PathVariable String username) {
        Device createDevice = modelMapper.map(newDeviceRequest, Device.class);

        deviceService.createByUsername(createDevice, username);
    }

    @PostMapping("attach/{deviceId}/{advertisementId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void attachByAdvertisementId(@PathVariable Long deviceId, @PathVariable Long advertisementId){
        deviceService.attachByAdvertisementId(deviceId, advertisementId);
    }

    @PutMapping("{deviceId}")
    public void updateByDeviceId(@PathVariable Long deviceId){
        deviceService.updateById(deviceId);
    }

    @DeleteMapping("{deviceId}")
    public void toggleDeviceStatusById(@PathVariable Long deviceId){
        deviceService.toggleStatusById(deviceId);
    }

    @DeleteMapping("{deviceId}/delete")
    public void deleteForever(@PathVariable Long deviceId){

    }

}
