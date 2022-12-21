package by.bsu.advertisement.service.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeviceDto {
    private Long id;
    private String title;
    private Integer impressionPerHour;
    private Boolean isActive;
    private List<AdvertisementDto> advertisements;
    private CutPersonDto person;
}
