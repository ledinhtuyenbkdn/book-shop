package com.ledinhtuyenbkdn.bookshop.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Service
public class StorageService {
    private Cloudinary cloudinary;

    public StorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String store(MultipartFile multipartFile) {
        try {
            File file = Files.createTempFile("temp", multipartFile.getOriginalFilename()).toFile();
            multipartFile.transferTo(file);
            Map<String, String> result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            return result.get("public_id");
        } catch (Exception e) {
            return null;
        }
    }
    public String load(String imageId) {
        return cloudinary.url().generate(imageId);
    }
    public void delete(String imageId) {
        try {
            cloudinary.uploader().destroy(imageId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
