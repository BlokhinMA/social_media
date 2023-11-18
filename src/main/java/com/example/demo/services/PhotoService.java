package com.example.demo.services;

import com.example.demo.models.Photo;
import com.example.demo.models.PhotoComment;
import com.example.demo.models.PhotoRating;
import com.example.demo.models.PhotoTag;
import com.example.demo.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final FriendshipRepository friendshipRepository;

    public Photo showEntity(int id) {
        return photoRepository.findById(id);
    }

    public Photo show(int id, Principal principal) {
        Photo photo = photoRepository.findById(id);
        //photo.getCreationTimeStamp().
        photo.setTags(photoTagRepository.findAllByPhotoId(id));
        photo.setUserRating(photoRatingRepository.findByRatingUserLoginAndPhotoId(principal.getName(), id));
        photo.setRating(photoRatingRepository.rating(id));
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

    public boolean createTag(PhotoTag photoTag) {
        if (photoTagRepository.findByTagAndPhotoId(photoTag) != null)
            return false;
        photoTagRepository.save(photoTag);
        return true;
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

    public List<Photo> find(String searchTerm, String word) {
        if (searchTerm != null && !searchTerm.isEmpty() && word != null && !word.isEmpty())
            switch (searchTerm) {
                case "creationTimeStamp" -> {
                    return photoRepository.findLikeCreationTimeStamp(word);
                }
                case "tags" -> {
                    return photoRepository.findLikeTags(word);
                }
                case "comments" -> {
                    return photoRepository.findLikeComments(word);
                }
            }
        return null;
    }

    public boolean isFriend(Principal principal, String userLogin) {
        /*List<User> friends = friendshipRepository.findAllAcceptedByFirstUserLoginOrSecondUserLogin(userLogin);
        for (User friend : friends) {
            if (Objects.equals(friend.getLogin(), principal.getName()))
                return true;
        }*/
        return friendshipRepository.findByFriendLoginAndLogin(principal.getName(), userLogin) != null;
    }

}
