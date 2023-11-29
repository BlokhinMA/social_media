package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PhotoService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public Photo showEntity(int id) {
        return photoRepository.findById(id);
    }

    public Photo show(int id, Principal principal) {
        Photo photo = photoRepository.findById(id);
        //photo.getCreationTimeStamp().
        photo.setTags(photoTagRepository.findAllByPhotoId(id));
        photo.setUserRating(photoRatingRepository.findByRatingUserLoginAndPhotoId(principal.getName(), id));
        photo.setRating(photoRatingRepository.calculateAverageRatingByPhotoId(id));
        photo.setComments(photoCommentRepository.findAllByPhotoId(id));
        photo.setAlbum(albumRepository.findById(photo.getAlbumId()));
        return photo;
    }

    public boolean delete(Photo photo, Principal principal) {
        if (albumRepository.findById(photo.getAlbumId()) == null)
            return false;
        int photoId = photo.getId();
        List<PhotoTag> tags = photoTagRepository.findAllByPhotoId(photoId);
        List<PhotoRating> ratings = photoRatingRepository.findAllByPhotoId(photoId);
        List<PhotoComment> comments = photoCommentRepository.findAllByPhotoId(photoId);
        Photo deletedPhoto = photoRepository.deleteById(photo.getId());
        deletedPhoto.setTags(tags);
        deletedPhoto.setRatings(ratings);
        deletedPhoto.setComments(comments);
        deletedPhoto.setAlbum(albumRepository.findById(deletedPhoto.getAlbumId()));
        log.info("Пользователь {} удалил фотографию {}",
                userRepository.findByLogin(principal.getName()),
                deletedPhoto);
        return true;
    }

    public boolean createTag(PhotoTag photoTag, Principal principal) {
        if (photoTagRepository.findByTagAndPhotoId(photoTag) != null)
            return false;
        PhotoTag createdPhotoTag = photoTagRepository.save(photoTag);
        log.info("Пользователь {} добавил тег {}",
                userRepository.findByLogin(principal.getName()),
                createdPhotoTag);
        return true;
    }

    public boolean deleteTag(PhotoTag photoTag, Principal principal) {
        if (photoRepository.findById(photoTag.getPhotoId()) == null)
            return false;
        PhotoTag deletedPhotoTag = photoTagRepository.deleteById(photoTag.getId());
        log.info("Пользователь {} удалил тег {}",
                userRepository.findByLogin(principal.getName()),
                deletedPhotoTag);
        return true;
    }

    public boolean createRating(PhotoRating photoRating, Principal principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return false;
        photoRating.setRatingUserLogin(principal.getName());
        PhotoRating createdPhotoRating = photoRatingRepository.save(photoRating);
        log.info("Пользователь {} поставил оценку {}",
                userRepository.findByLogin(principal.getName()),
                createdPhotoRating);
        return true;
    }

    public boolean updateRating(PhotoRating photoRating, Principal principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return false;
        PhotoRating updatedPhotoRating = photoRatingRepository.updateRatingById(photoRating);
        log.info("Пользователь {} обновил оценку {}",
                userRepository.findByLogin(principal.getName()),
                updatedPhotoRating);
        return true;
    }

    public boolean deleteRating(PhotoRating photoRating, Principal principal) {
        if (photoRepository.findById(photoRating.getPhotoId()) == null)
            return false;
        PhotoRating deletedPhotoRating = photoRatingRepository.deleteById(photoRating.getId());
        log.info("Пользователь {} удалил оценку {}",
                userRepository.findByLogin(principal.getName()),
                deletedPhotoRating);
        return true;
    }

    public void createComment(PhotoComment photoComment, Principal principal) {
        photoComment.setComment(photoComment.getComment());
        photoComment.setCommentingUserLogin(principal.getName());
        photoComment.setPhotoId(photoComment.getPhotoId());
        PhotoComment createdPhotoComment = photoCommentRepository.save(photoComment);
        log.info("Пользователь {} добавил комментарий {}",
                userRepository.findByLogin(principal.getName()),
                createdPhotoComment);
    }

    public boolean deleteComment(PhotoComment photoComment, Principal principal) {
        if (photoRepository.findById(photoComment.getPhotoId()) == null)
            return false;
        PhotoComment deletedPhotoComment = photoCommentRepository.deleteById(photoComment.getId());
        log.info("Пользователь {} удалил комментарий {}",
                userRepository.findByLogin(principal.getName()),
                deletedPhotoComment);
        return true;
    }

    public List<Photo> find(String searchTerm, String word) {
        if (searchTerm != null && !searchTerm.isEmpty() && word != null && !word.isEmpty())
            switch (searchTerm) {
                case "creationTimeStamp" -> {
                    return photoRepository.findAllLikeCreationTimeStamp(word);
                }
                case "tags" -> {
                    return photoRepository.findAllLikeTags(word);
                }
                case "comments" -> {
                    return photoRepository.findAllLikeComments(word);
                }
            }
        return null;
    }

    public boolean isFriend(Principal principal, String userLogin) {
        return friendshipRepository.findByFriendLoginAndUserLogin(principal.getName(), userLogin) != null;
    }

}
