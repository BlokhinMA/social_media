package com.example.demo.services;

import com.example.demo.models.Community;
import com.example.demo.models.CommunityMember;
import com.example.demo.repositories.CommunityPostRepository;
import com.example.demo.repositories.CommunityRepository;
import com.example.demo.repositories.CommunityMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    public List<Community> showAll(Principal principal) {
        return communityRepository.findAllByCreatorLogin(principal.getName());
    }

    public boolean add(Community community, Principal principal) {
        if (community.getName() == null)
            return false;
        community.setCreatorLogin(principal.getName());
        Community newCommunity = communityRepository.save(community);
        join(principal.getName(), newCommunity.getId());
        return true;
    }

    public Community show(int id) {
        Community community = communityRepository.findById(id);
        community.setMembers(communityMemberRepository.findAllByCommunityId(id));
        community.setPosts(communityPostRepository.findAllByCommunityId(id));
        return community;
    }

    public void join(String memberLogin, int communityId) {
        CommunityMember communityMember = new CommunityMember();
        communityMember.setMemberLogin(memberLogin);
        communityMember.setCommunityId(communityId);
        communityMemberRepository.save(communityMember);
    }

}
