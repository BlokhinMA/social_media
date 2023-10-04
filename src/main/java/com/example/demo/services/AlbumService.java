package com.example.demo.services;

import com.example.demo.models.Album;
import com.example.demo.models.Photo;
import com.example.demo.repositories.AlbumRepository;
import com.example.demo.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public boolean addAlbum(Album album, String userUsername, List<MultipartFile> files) throws IOException {
        if (album.getName() == null)
            return false;
        albumRepository.save(album, userUsername);
        if (!files.isEmpty()) {
            List<Photo> photos = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                photos.add(toPhotoEntity(files.get(i)));
                photos.get(i).setAlbumId(albumRepository.findLastId());
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

    public List<Album> showAlbums(String userUsername) {
        return albumRepository.findAllByUserId(userUsername);
    }

}
