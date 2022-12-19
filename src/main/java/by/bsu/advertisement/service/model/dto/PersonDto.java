package by.bsu.advertisement.service.model.dto;

import by.bsu.advertisement.service.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean isBlocked;
    private List<DeviceDto> devices;
    private List<AdvertisementDto> advertisements;
}
