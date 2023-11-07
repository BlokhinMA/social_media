package com.example.demo.services;

import com.example.demo.models.Photo;
import com.example.demo.models.PhotoComment;
import com.example.demo.models.PhotoTag;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;

    @Autowired
    public PhotoService(AlbumRepository albumRepository, PhotoRepository photoRepository, PhotoTagRepository photoTagRepository, PhotoRatingRepository photoRatingRepository, PhotoCommentRepository photoCommentRepository) {
        this.albumRepository = albumRepository;
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
        photo.setAlbum(albumRepository.findById(photo.getAlbumId()));
        return photo;
    }

    public void createTag(PhotoTag photoTag) {
        photoTagRepository.save(photoTag);
    }

    public void createComment(PhotoComment photoComment, Principal principal) {
        photoComment.setComment(photoComment.getComment());
        photoComment.setCommentingUserLogin(principal.getName());
        photoComment.setPhotoId(photoComment.getPhotoId());
        photoCommentRepository.save(photoComment);
    }

    public boolean delete(Photo photo) {
        if (albumRepository.findById(photo.getAlbumId()) == null)
            return false;
        photoRepository.delete(photo);
        return true;
    }

    public boolean deleteTag(PhotoTag photoTag) {
        if (photoRepository.findById(photoTag.getPhotoId()) == null)
            return false;
        photoTagRepository.delete(photoTag);
        return true;
    }

    public boolean deleteComment(PhotoComment photoComment) {
        if (photoRepository.findById(photoComment.getPhotoId()) == null)
            return false;
        photoCommentRepository.delete(photoComment);
        return true;
    }

}
