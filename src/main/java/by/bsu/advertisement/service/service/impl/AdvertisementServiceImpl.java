package by.bsu.advertisement.service.service.impl;

import by.bsu.advertisement.service.model.Advertisement;
import by.bsu.advertisement.service.repository.AdvertisementRepository;
import by.bsu.advertisement.service.service.AdvertisementService;
import by.bsu.advertisement.service.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<Advertisement> getAll() {
        return advertisementRepository.findAll();
    }

    @Override
    public void create(Advertisement advertisement, MultipartFile multipartFile) {
        String imageUrl = cloudinaryService.uploadFile(multipartFile);
        advertisement.setImageUrl(imageUrl);

        advertisementRepository.save(advertisement);
    }


}
