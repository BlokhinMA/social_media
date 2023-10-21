package com.example.demo.services;

import com.example.demo.models.Album;
import com.example.demo.models.Photo;
import com.example.demo.repositories.AlbumRepository;
import com.example.demo.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, PhotoRepository photoRepository) {
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
    }

    public boolean add(Album album, List<MultipartFile> files, Principal principal) throws IOException {
        if (album.getName() == null)
            return false;
        album.setUserLogin(principal.getName());
        Album newAlbum = albumRepository.save(album);
        if (!files.isEmpty()) {
            List<Photo> photos = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                photos.add(toPhotoEntity(files.get(i)));
                photos.get(i).setAlbumId(newAlbum.getId());
            }
            photoRepository.save(photos);
        }
        return true;
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

    public Album show(int id) {
        Album album = albumRepository.findById(id);
        album.setPhotos(photoRepository.findAllByAlbumId(id));
        return album;
    }

    public boolean addPhotos(List<MultipartFile> files, int albumId) throws IOException {
        if (files.isEmpty())
            return false;
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            photos.add(toPhotoEntity(files.get(i)));
            photos.get(i).setAlbumId(albumId);
        }
        photoRepository.save(photos);
        return true;
    }

}
