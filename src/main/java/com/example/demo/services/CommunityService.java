package com.example.demo.services;

import com.example.demo.models.Community;
import com.example.demo.models.CommunityMember;
import com.example.demo.models.CommunityPost;
import com.example.demo.repositories.CommunityPostRepository;
import com.example.demo.repositories.CommunityRepository;
import com.example.demo.repositories.CommunityMemberRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserRepository userRepository;

    public List<Community> showAllOwn(Principal principal) {
        return communityRepository.findAllByCreatorLogin(principal.getName());
    }

    public List<Community> showAll(Principal principal) {
        return communityRepository.findAllByMemberLogin(principal.getName());
    }

    public List<Community> showAll(String memberLogin) {
        return communityRepository.findAllByMemberLogin(memberLogin);
    }

    public void create(Community community, Principal principal) {
        community.setCreatorLogin(principal.getName());
        Community createdCommunity = communityRepository.save(community);
        log.info("Пользователь {} добавил сообщество {}",
                userRepository.findByLogin(principal.getName()),
                createdCommunity);
    }

    public void delete(int id, Principal principal) {
        List<CommunityMember> members = communityMemberRepository.findAllByCommunityId(id);
        List<CommunityPost> posts = communityPostRepository.findAllByCommunityId(id);
        Community deletedCommunity = communityRepository.deleteById(id);
        deletedCommunity.setMembers(members);
        deletedCommunity.setPosts(posts);
        log.info("Пользователь {} удалил сообщество {}",
                userRepository.findByLogin(principal.getName()),
                deletedCommunity);
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
        CommunityMember joinedCommunityMember = communityMemberRepository.save(communityMember);
        joinedCommunityMember.setCommunity(communityRepository.findById(joinedCommunityMember.getCommunityId()));
        log.info("Пользователь {} стал участником сообщества {}",
                userRepository.findByLogin(principal.getName()),
                joinedCommunityMember);
        return true;
    }

    public boolean isMember(Principal principal, int communityId) {
        return communityMemberRepository.findByMemberLoginAndCommunityId(principal.getName(), communityId) != null;
    }

    public boolean leave(Principal principal, CommunityMember communityMember) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return false;
        communityMember.setMemberLogin(principal.getName());
        CommunityMember leftCommunityMember = communityMemberRepository.delete(communityMember);
        leftCommunityMember.setCommunity(communityRepository.findById(leftCommunityMember.getCommunityId()));
        log.info("Пользователь {} перестал быть участником сообщества {}",
                userRepository.findByLogin(principal.getName()),
                leftCommunityMember);
        return true;
    }

    public boolean kickCommunityMember(CommunityMember communityMember, Principal principal) {
        if (communityRepository.findById(communityMember.getCommunityId()) == null)
            return false;
        CommunityMember kickedCommunityMember = communityMemberRepository.deleteById(communityMember.getId());
        kickedCommunityMember.setCommunity(communityRepository.findById(kickedCommunityMember.getCommunityId()));
        log.info("Пользователь {} выгнал участника сообщества {}",
                userRepository.findByLogin(principal.getName()),
                kickedCommunityMember);
        return true;
    }

    public void createPost(CommunityPost communityPost, Principal principal) {
        communityPost.setAuthorLogin(principal.getName());
        CommunityPost createdPost = communityPostRepository.save(communityPost);
        createdPost.setCommunity(communityRepository.findById(createdPost.getCommunityId()));
        log.info("Пользователь {} добавил пост {}",
                userRepository.findByLogin(principal.getName()),
                createdPost);
    }

    public boolean deletePost(CommunityPost communityPost, Principal principal) {
        if (communityRepository.findById(communityPost.getCommunityId()) == null)
            return false;
        CommunityPost deletedCommunityPost = communityPostRepository.delete(communityPost);
        deletedCommunityPost.setCommunity(communityRepository.findById(deletedCommunityPost.getCommunityId()));
        log.info("Пользователь {} удалил пост {}",
                userRepository.findByLogin(principal.getName()),
                deletedCommunityPost);
        return true;
    }

    public List<Community> find(String word) {
        if (word != null && !word.isEmpty())
            return communityRepository.findLikeName(word);
        return null;
    }

}
