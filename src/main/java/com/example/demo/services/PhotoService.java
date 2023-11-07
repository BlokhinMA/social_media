package com.example.demo.services;

import com.example.demo.models.Photo;
import com.example.demo.models.PhotoComment;
import com.example.demo.models.PhotoRating;
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

    public Photo show(int id, Principal principal) {
        Photo photo = photoRepository.findById(id);
        photo.setTags(photoTagRepository.findAllByPhotoId(id));
        photo.setUserRating(photoRatingRepository.findByRatingUserLoginAndPhotoId(principal.getName(), id));
        Double rating = photoRatingRepository.rating(id);
        if (rating != null)
            photo.setRating(rating * 100);
        photo.setComments(photoCommentRepository.findAllByPhotoId(id));
        photo.setAlbum(albumRepository.findById(photo.getAlbumId()));
        return photo;
    }

    public boolean delete(Photo photo) {
        if (albumRepository.findById(photo.getAlbumId()) == null)
            return false;
        photoRepository.delete(photo);
        return true;
    }

    public void createTag(PhotoTag photoTag) {
        photoTagRepository.save(photoTag);
    }

    public boolean deleteTag(PhotoTag photoTag) {
        if (photoRepository.findById(photoTag.getPhotoId()) == null)
            return false;
        photoTagRepository.delete(photoTag);
        return true;
    }

    public boolean createRating(PhotoRating photoRating, Principal principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return false;
        photoRating.setRatingUserLogin(principal.getName());
        photoRatingRepository.save(photoRating);
        return true;
    }

    public boolean updateRating(PhotoRating photoRating) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return false;
        photoRatingRepository.update(photoRating);
        return true;
    }

    public boolean deleteRating(PhotoRating photoRating) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return false;
        photoRatingRepository.delete(photoRating);
        return true;
    }

    public void createComment(PhotoComment photoComment, Principal principal) {
        photoComment.setComment(photoComment.getComment());
        photoComment.setCommentingUserLogin(principal.getName());
        photoComment.setPhotoId(photoComment.getPhotoId());
        photoCommentRepository.save(photoComment);
    }

    public boolean deleteComment(PhotoComment photoComment) {
        if (photoRepository.findById(photoComment.getPhotoId()) == null)
            return false;
        photoCommentRepository.delete(photoComment);
        return true;
    }

}
