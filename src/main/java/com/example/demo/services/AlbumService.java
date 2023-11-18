package com.example.demo.services;

import com.example.demo.models.Album;
import com.example.demo.models.Photo;
import com.example.demo.repositories.AlbumRepository;
import com.example.demo.repositories.FriendshipRepository;
import com.example.demo.repositories.PhotoRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public void create(Album album, List<MultipartFile> files, Principal principal) throws IOException {
        album.setUserLogin(principal.getName());
        Album newAlbum = albumRepository.save(album);
        log.info("Пользователь {} добавил альбом {}",  newAlbum);
        if (!files.isEmpty()) {
            Photo photo;
            for (MultipartFile file : files) {
                photo = toPhotoEntity(file);
                photo.setAlbumId(newAlbum.getId());
                photoRepository.save(photo);
            }
        }
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

    public void delete(int id) {
        albumRepository.deleteById(id);
    }

    public boolean createPhotos(List<MultipartFile> files, int albumId) throws IOException {
        if (files.isEmpty())
            return false;
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            photos.add(toPhotoEntity(files.get(i)));
            photos.get(i).setAlbumId(albumId);
            photoRepository.save(photos.get(i));
        }
        return true;
    }

    public List<Album> find(String word) {
        if (word != null && !word.isEmpty())
            return albumRepository.findLikeName(word);
        return null;
    }

    public boolean changeAccessType(Album album) {
        if (albumRepository.findById(album.getId()) == null)
            return false;
        albumRepository.updateAccessTypeById(album);
        return true;
    }

    public boolean isFriend(Principal principal, String userLogin) {
        return friendshipRepository.findByFriendLoginAndLogin(principal.getName(), userLogin) != null;
    }

}
