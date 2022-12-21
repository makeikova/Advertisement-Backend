package by.bsu.advertisement.service.service.impl;

import by.bsu.advertisement.service.model.Advertisement;
import by.bsu.advertisement.service.model.Device;
import by.bsu.advertisement.service.repository.AdvertisementRepository;
import by.bsu.advertisement.service.repository.DeviceRepository;
import by.bsu.advertisement.service.service.AdvertisementService;
import by.bsu.advertisement.service.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final DeviceRepository deviceService;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<Advertisement> getAll(Boolean isAppear) {
        return advertisementRepository.findAllByIsAppear(isAppear);
    }

    @Override
    public List<Advertisement> getAllWithoutAppear() {
        return advertisementRepository.findAll();
    }

    @Override
    public void create(Advertisement advertisement, MultipartFile multipartFile) {
        String imageUrl = cloudinaryService.uploadFile(multipartFile);
        advertisement.setImageUrl(imageUrl);
        advertisement.setIsAppear(true);
        advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement getById(Long id) {
        return advertisementRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Advertisement with id: %s not exists!", id)));
    }

    @Override
    public void updateById(Long id, Advertisement advertisement) {
        Advertisement dbAdvertisement = advertisementRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Advertisement with id: %s not exists!", id)));
        dbAdvertisement.setTitle(advertisement.getTitle());
        dbAdvertisement.setDescription(advertisement.getDescription());

        advertisementRepository.save(dbAdvertisement);
    }

    @Override
    public void hideById(Long id) {
        Advertisement advertisement = advertisementRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Advertisement with id: %s not exists!", id)));
        boolean isAppear = advertisement.getIsAppear();
        advertisement.setIsAppear(!isAppear);
        advertisement.setAttachTime(LocalDateTime.now());
        advertisementRepository.save(advertisement);
    }

    @Override
    public List<Advertisement> getAllByPersonUsernameAndAppear(Boolean isAppear, String username) {
        return advertisementRepository.findAllByIsAppearAndPersonUsername(isAppear, username);
    }

    @Override
    public List<Advertisement> getAllByPersonUsername(String username) {
        return advertisementRepository.findAllByPersonUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        advertisementRepository.deleteById(id);
    }
}
