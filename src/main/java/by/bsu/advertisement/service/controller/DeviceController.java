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
    public List<DeviceDto> getAll(){
        List<Device> devices = deviceService.getAll();
        return devices.stream()
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("{personId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateNewDeviceRequest newDeviceRequest,
                       @PathVariable Long personId) {
        Device createDevice = modelMapper.map(newDeviceRequest, Device.class);

        deviceService.createByUserId(createDevice, personId);
    }

}
