package com.gabsleo.meaudote.services;

import com.gabsleo.meaudote.entities.AdoptionAnimal;
import com.gabsleo.meaudote.exceptions.NotFoundException;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class StorageService {
    private final Storage storage;
    private final AdoptionAnimalService adoptionAnimalService;
    private final AppUserService appUserService;

    @Value("${env.GCP_BUCKET_NAME}")
    private String bucket_name;

    @Autowired
    public StorageService(Storage storage, AdoptionAnimalService adoptionAnimalService, AppUserService appUserService) {
        this.storage = storage;
        this.adoptionAnimalService = adoptionAnimalService;
        this.appUserService = appUserService;
    }

    public void save( String path, MultipartFile mpfile ) throws IOException {
        BlobId id = BlobId.of(bucket_name, path+ "/" + mpfile.getOriginalFilename());
        BlobInfo info = BlobInfo.newBuilder(id).build();
        storage.create(info, mpfile.getBytes());
    }

    public void save(UUID id, String path, MultipartFile file) throws IOException {
        this.save(path + "/" + id, file);
        adoptionAnimalService.save(adoptionAnimalService.findById(id).setImage(file.getOriginalFilename()));
    }

    public void saveAppUserProfilePicture(String name, String path, MultipartFile file) throws IOException, NotFoundException {
        this.save(path, file);
        appUserService.save(appUserService.findByName(name).setProfilePicture(file.getOriginalFilename()));
    }

    public void saveAppUserBannerPicture(String username, String path, MultipartFile file) throws IOException, NotFoundException {
        this.save(path, file);
        appUserService.save(appUserService.findByName(username).setBannerPicture(file.getOriginalFilename()));
    }

    public byte[] download(String path) throws IOException {
        try (ReadChannel channel = storage.reader(bucket_name, path)) {
            ByteBuffer bytes = ByteBuffer.allocate(1024*1024);
            while(channel.read(bytes) > 0) {
                bytes.flip();
                bytes.clear();
            }
            return bytes.array();
        }
    }

    public byte[] download(UUID id, String path) throws IOException {
        AdoptionAnimal pet = adoptionAnimalService.findById(id);
        return this.download(path + "/" + id + "/" + pet.getImage());
    }

}
