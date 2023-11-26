package com.example.demo.services;

import com.example.demo.models.Album;
import com.example.demo.models.Photo;
import com.example.demo.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final PhotoTagRepository photoTagRepository;
    private final PhotoRatingRepository photoRatingRepository;
    private final PhotoCommentRepository photoCommentRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public void create(Album album, List<MultipartFile> files, Principal principal) throws IOException {
        album.setUserLogin(principal.getName());
        Album createdAlbum = albumRepository.save(album);
        log.info("Пользователь {} добавил альбом {}",
                userRepository.findByLogin(principal.getName()),
                createdAlbum);
        createPhotos(files, createdAlbum.getId(), principal);
    }

    private Photo toPhotoEntity(MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setName(file.getName());
        photo.setOriginalFileName(file.getOriginalFilename());
        photo.setSize(file.getSize());
        photo.setContentType(file.getContentType());
        photo.setBytes(file.getBytes());
        return photo;
    }

    public List<Album> showAll(Principal principal) {
        return albumRepository.findAllByUserLogin(principal.getName());
    }

    public List<Album> showAll(String userLogin) {
        return albumRepository.findAllByUserLogin(userLogin);
    }

    public Album show(int id) {
        Album album = albumRepository.findById(id);
        album.setPhotos(photoRepository.findAllByAlbumId(id));
        return album;
    }

    public void delete(int id, Principal principal) {
        List<Photo> photos = photoRepository.findAllByAlbumId(id);
        for (Photo photo : photos) {
            int photoId = photo.getId();
            photo.setTags(photoTagRepository.findAllByPhotoId(photoId));
            photo.setRatings(photoRatingRepository.findAllByPhotoId(photoId));
            photo.setComments(photoCommentRepository.findAllByPhotoId(photoId));
        }
        Album deletedAlbum = albumRepository.deleteById(id);
        deletedAlbum.setPhotos(photos);
        log.info("Пользователь {} удалил альбом {}",
                userRepository.findByLogin(principal.getName()),
                deletedAlbum);
    }

    public boolean createPhotos(List<MultipartFile> files, int albumId, Principal principal) throws IOException {
        if (Objects.requireNonNull(files.get(0).getOriginalFilename()).isEmpty())
            return false;
        List<Photo> photos = new ArrayList<>();
        List<Photo> createdPhotos = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            photos.add(toPhotoEntity(files.get(i)));
            //photos.add(new Photo());
            photos.get(i).setAlbumId(albumId);
            String originalFilename = files.get(i).getOriginalFilename();
            //photos.get(i).setOriginalFileName(originalFilename);
            Photo createdPhoto = photoRepository.save(photos.get(i));
            files.get(i).transferTo(new File(uploadPath + "/" + originalFilename));
            createdPhoto.setAlbum(albumRepository.findById(createdPhoto.getAlbumId()));
            createdPhotos.add(createdPhoto);
        }
        log.info("Пользователь {} добавил фотографии {}",
                userRepository.findByLogin(principal.getName()),
                createdPhotos);
        return true;
    }

    public List<Album> find(String word) {
        if (word != null && !word.isEmpty())
            return albumRepository.findAllLikeName(word);
        return null;
    }

    public boolean changeAccessType(Album album, Principal principal) {
        if (albumRepository.findById(album.getId()) == null)
            return false;
        Album updatedAlbum = albumRepository.updateAccessTypeById(album);
        log.info("Пользователь {} обновил альбом {}",
                userRepository.findByLogin(principal.getName()),
                updatedAlbum);
        return true;
    }

    public boolean isFriend(Principal principal, String userLogin) {
        return friendshipRepository.findByFriendLoginAndLogin(principal.getName(), userLogin) != null;
    }

}
