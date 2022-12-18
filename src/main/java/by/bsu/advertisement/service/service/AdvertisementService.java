package by.bsu.advertisement.service.service;

import by.bsu.advertisement.service.model.Advertisement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdvertisementService {
    List<Advertisement> getAll(Boolean isAppear);
    List<Advertisement> getAllWithoutAppear();
    void create(Advertisement advertisement, MultipartFile multipartFile);
    Advertisement getById(Long id);
    void updateById(Long id, Advertisement advertisement);
    void hideById(Long id);
    List<Advertisement> getAllByPersonUsername(String username);
}
