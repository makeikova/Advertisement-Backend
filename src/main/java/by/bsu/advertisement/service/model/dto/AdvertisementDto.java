package by.bsu.advertisement.service.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdvertisementDto {
    private Long id;
    private String title;
    private String description;
    private Boolean isAppear;
    private LocalDateTime attachTime;
    private String imageUrl;
}
