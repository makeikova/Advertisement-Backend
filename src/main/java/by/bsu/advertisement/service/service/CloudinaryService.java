package by.bsu.advertisement.service.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(MultipartFile multipartFile);
}
