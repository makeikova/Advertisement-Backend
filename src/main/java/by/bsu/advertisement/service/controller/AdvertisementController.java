package by.bsu.advertisement.service.controller;

import by.bsu.advertisement.service.model.Advertisement;
import by.bsu.advertisement.service.model.dto.AdvertisementDto;
import by.bsu.advertisement.service.model.request.AdvertisementUpdateRequest;
import by.bsu.advertisement.service.model.request.CreateNewAdvertisement;
import by.bsu.advertisement.service.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<AdvertisementDto> getAll(@RequestParam Boolean isAppear){
        List<Advertisement> allAdvertisements = advertisementService.getAll(isAppear);

        return allAdvertisements.stream()
                .map(advertisement -> modelMapper.map(advertisement, AdvertisementDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public AdvertisementDto getById(@PathVariable Long id){
        Advertisement advertisement = advertisementService.getById(id);
        return modelMapper.map(advertisement, AdvertisementDto.class);
    }

    @PostMapping
    public void create(@RequestParam String title,
                       @RequestParam String description,
                       @RequestParam(value = "file") MultipartFile file){
        CreateNewAdvertisement createNewAdvertisement = new CreateNewAdvertisement(title, description);
        Advertisement advertisement = modelMapper.map(createNewAdvertisement, Advertisement.class);
        advertisementService.create(advertisement, file);
    }

    @PutMapping("{id}")
    public void updateById(@PathVariable Long id, @RequestBody AdvertisementUpdateRequest updateRequest){
        Advertisement advertisement = modelMapper.map(updateRequest, Advertisement.class);
        advertisementService.updateById(id, advertisement);
    }

    @DeleteMapping("{id}")
    public void hideById(@PathVariable Long id){
        advertisementService.hideById(id);
    }

}
