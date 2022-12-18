package by.bsu.advertisement.service.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeviceDto {
    private String title;
    private Integer impressionPerHour;
    private Boolean isActive;
    private List<AdvertisementDto> advertisements;
}
