package by.bsu.advertisement.service.service;

import by.bsu.advertisement.service.model.Advertisement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdvertisementService {
    List<Advertisement> getAll();
    void create(Advertisement advertisement, MultipartFile multipartFile);
}
