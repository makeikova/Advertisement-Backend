package by.bsu.advertisement.service.controller;

import by.bsu.advertisement.service.model.Advertisement;
import by.bsu.advertisement.service.model.Person;
import by.bsu.advertisement.service.model.dto.AdvertisementDto;
import by.bsu.advertisement.service.model.request.AdvertisementUpdateRequest;
import by.bsu.advertisement.service.model.request.CreateNewAdvertisement;
import by.bsu.advertisement.service.service.AdvertisementService;
import by.bsu.advertisement.service.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final PersonService personService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    public List<AdvertisementDto> getAllAdvertisement(){
        List<Advertisement> all = advertisementService.getAllWithoutAppear();
        return all.stream()
                .map(el -> modelMapper.map(el, AdvertisementDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<AdvertisementDto> getAll(@RequestParam Boolean isAppear){
        List<Advertisement> allAdvertisements = advertisementService.getAll(isAppear);

        return allAdvertisements.stream()
                .map(advertisement -> modelMapper.map(advertisement, AdvertisementDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/my/{username}")
    public List<AdvertisementDto> getAllByUsername(@PathVariable String username){
        List<Advertisement> advertisements = advertisementService.getAllByPersonUsername(username);

        return advertisements
                .stream()
                .map(el -> modelMapper.map(el, AdvertisementDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public AdvertisementDto getById(@PathVariable Long id){
        Advertisement advertisement = advertisementService.getById(id);
        return modelMapper.map(advertisement, AdvertisementDto.class);
    }

    @PostMapping("{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable String username,
                       @RequestParam String title,
                       @RequestParam String description,
                       @RequestParam MultipartFile file){
        CreateNewAdvertisement createNewAdvertisement = new CreateNewAdvertisement(title, description, username);
        Person person = personService.findByUsername(username);
        Advertisement advertisement = modelMapper.map(createNewAdvertisement, Advertisement.class);
        advertisement.setPerson(person);
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
