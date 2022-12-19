package by.bsu.advertisement.service.model.dto;

import lombok.Data;

@Data
public class AdvertisementDto {
    private Long id;
    private String title;
    private String description;
    private Boolean isAppear;
    private String imageUrl;
}
