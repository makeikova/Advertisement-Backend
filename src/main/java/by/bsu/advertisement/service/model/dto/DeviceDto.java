package by.bsu.advertisement.service.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeviceDto {
    private String title;
    private String description;
    private Integer impressionPerHour;
    private List<AdvertisementDto> advertisements;
}
