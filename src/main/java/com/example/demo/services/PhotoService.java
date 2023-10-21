package com.example.demo.services;

import com.example.demo.models.Photo;
import com.example.demo.models.PhotoTag;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, PhotoTagRepository photoTagRepository, PhotoRatingRepository photoRatingRepository, PhotoCommentRepository photoCommentRepository) {
        this.photoRepository = photoRepository;
        this.photoTagRepository = photoTagRepository;
        this.photoRatingRepository = photoRatingRepository;
        this.photoCommentRepository = photoCommentRepository;
    }

    public Photo showEntity(int id) {
        return photoRepository.findById(id);
    }

    public Photo show(int id) {
        Photo photo = photoRepository.findById(id);
        photo.setTags(photoTagRepository.findAllByPhotoId(id));
        photo.setRatings(photoRatingRepository.findAllByPhotoId(id));
        photo.setComments(photoCommentRepository.findAllByPhotoId(id));
        return photo;
    }

    public boolean addTags(String string, int photoId) {
        if (string == null) {
            return false;
        }
        String[] tags = string.split(", ");
        PhotoTag photoTag = new PhotoTag();
        for (String tag : tags) {
            photoTag.setTag(tag);
            photoTag.setPhotoId(photoId);
            photoTagRepository.save(photoTag);
        }
        return true;
    }

}
