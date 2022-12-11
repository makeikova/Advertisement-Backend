package by.bsu.advertisement.service.model.request;

import lombok.Data;

@Data
public class CreateNewPersonRequest {
    private String username;
    private String email;
    private String password;
}
