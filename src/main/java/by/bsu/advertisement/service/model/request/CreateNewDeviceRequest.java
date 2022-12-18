package by.bsu.advertisement.service.model.request;

import lombok.Data;

@Data
public class CreateNewDeviceRequest {
    private String title;
    private Integer impressionPerHour;
}
