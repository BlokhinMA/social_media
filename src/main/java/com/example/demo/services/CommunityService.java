package com.example.demo.services;

import com.example.demo.models.Community;
import com.example.demo.models.CommunityMember;
import com.example.demo.repositories.CommunityPostRepository;
import com.example.demo.repositories.CommunityRepository;
import com.example.demo.repositories.CommunityMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository, CommunityMemberRepository communityMemberRepository, CommunityPostRepository communityPostRepository) {
        this.communityRepository = communityRepository;
        this.communityMemberRepository = communityMemberRepository;
        this.communityPostRepository = communityPostRepository;
    }

    public boolean addCommunity(Community community, String creatorUsername) {
        if (community.getName() == null)
            return false;
        communityRepository.save(community, creatorUsername);
        CommunityMember communityMember = new CommunityMember();
        communityMember.setCommunityId(communityRepository.findLastId());
        communityMemberRepository.save(creatorUsername, communityMember);
        return true;
    }

    public List<Community> showCommunities(String creatorUsername) {
        return communityRepository.findAllByCreatorUsername(creatorUsername);
    }

    public Community showCommunity(int id) {
        Community community = communityRepository.findById(id);
        community.setMembers(communityMemberRepository.findAllByCommunityId(id));
        community.setPosts(communityPostRepository.findAllByCommunityId(id));
        return community;
    }

}
