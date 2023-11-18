package com.example.demo.services;

import com.example.demo.models.Community;
import com.example.demo.models.CommunityMember;
import com.example.demo.models.CommunityPost;
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

    public List<Community> showAllOwn(Principal principal) {
        return communityRepository.findAllByCreatorLogin(principal.getName());
    }

    public List<Community> showAll(Principal principal) {
        return communityRepository.findAllByMemberLogin(principal.getName());
    }

    public List<Community> showAll(String memberLogin) {
        return communityRepository.findAllByMemberLogin(memberLogin);
    }

    public boolean create(Community community, Principal principal) {
        community.setCreatorLogin(principal.getName());
        communityRepository.save(community);
        return true;
    }

    public void delete(int id) {
        communityRepository.deleteById(id);
    }

    public Community show(int id) {
        Community community = communityRepository.findById(id);
        community.setMembers(communityMemberRepository.findAllByCommunityId(id));
        community.setPosts(communityPostRepository.findAllByCommunityId(id));
        //community.getPosts().get(0).getCreationTimeStamp().
        return community;
    }

    public boolean join(Principal principal, int communityId) {
        if (communityRepository.findById(communityId) == null)
            return false;
        CommunityMember communityMember = new CommunityMember();
        communityMember.setMemberLogin(principal.getName());
        communityMember.setCommunityId(communityId);
        communityMemberRepository.save(communityMember);
        return true;
    }

    public boolean isMember(Principal principal, int communityId) {
        return communityMemberRepository.findByMemberLoginAndCommunityId(principal.getName(), communityId) != null;
    }

    public boolean leave(Principal principal, CommunityMember communityMember) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return false;
        communityMember.setMemberLogin(principal.getName());
        communityMemberRepository.delete(communityMember);
        return true;
    }

    public void createPost(CommunityPost communityPost, Principal principal) {
        communityPost.setAuthorLogin(principal.getName());
        communityPostRepository.save(communityPost);
    }

    public boolean deletePost(CommunityPost communityPost) {
        if (communityRepository.findById(communityPost.getCommunityId()) == null)
            return false;
        communityPostRepository.delete(communityPost);
        return true;
    }

    public List<Community> find(String word) {
        if (word != null && !word.isEmpty())
            return communityRepository.findLikeName(word);
        return null;
    }

}
