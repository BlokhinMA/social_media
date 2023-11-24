package com.example.demo.services;

import com.example.demo.models.Message;
import com.example.demo.repositories.MessageRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public void create(Message message, Principal principal) {
        Message createdMessage = messageRepository.save(message);
        log.info("Пользователь {} написал сообщение {} пользователю {}",
                userRepository.findByLogin(principal.getName()),
                createdMessage,
                userRepository.findByLogin(createdMessage.getToUserLogin()));
    }

    public List<Message> showAll(Principal principal) {
        return messageRepository.findAllByUserLogin(principal.getName());
    }

    public List<Message> show(String companion, Principal principal) {
        return messageRepository.findAllByFromUserLoginAndToUserLogin(companion, principal.getName());
    }

    public void delete(Message message, Principal principal) {
        Message deletedMessage = messageRepository.delete(message);
        log.info("Пользователь {} удалил сообщение {}",
                userRepository.findByLogin(principal.getName()),
                deletedMessage);
    }

}
