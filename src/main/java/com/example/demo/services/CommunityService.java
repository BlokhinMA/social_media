package com.example.demo.services;

import com.example.demo.models.Album;
import com.example.demo.models.Community;
import com.example.demo.repositories.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public boolean addCommunity(Community community, String creatorUsername) {
        if (community.getName() == null)
            return false;
        communityRepository.save(community, creatorUsername);
        return true;
    }

    public List<Community> showCommunities(String creatorUsername) {
        return communityRepository.findAllByCreatorUsername(creatorUsername);
    }

}
