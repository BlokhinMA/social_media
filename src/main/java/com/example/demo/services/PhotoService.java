package com.example.demo.services;

import com.example.demo.models.Photo;
import com.example.demo.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public boolean addPhotos(List<File> files) {
        List<Photo> photos = new ArrayList<>();

        photoRepository.save(photos);
        return true;
    }

}
