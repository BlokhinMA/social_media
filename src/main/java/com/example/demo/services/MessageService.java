package com.example.demo.services;

import com.example.demo.models.Message;
import com.example.demo.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void create(Message message) {
        messageRepository.save(message);
    }

    public List<Message> showAll(Principal principal) {
        return messageRepository.findAllByUserLogin(principal.getName());
    }

    public List<Message> show(String companion, Principal principal) {
        return messageRepository.findAllByFromUserLoginAndToUserLogin(companion, principal.getName());
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }

}
