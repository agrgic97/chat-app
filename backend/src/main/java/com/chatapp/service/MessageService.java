package com.chatapp.service;

import com.chatapp.dto.MessageDto;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.chatapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public MessageDto save(String fromUsername, String toUsername, String content) {
        User sender = userService.getByUsername(fromUsername);
        User recipient = userService.getByUsername(toUsername);
        Message message = new Message(sender, recipient, content);
        Message saved = messageRepository.save(message);
        return toDto(saved);
    }

    public List<MessageDto> conversation(String userA, String userB) {
        User a = userService.getByUsername(userA);
        User b = userService.getByUsername(userB);
        return messageRepository
                .findBySenderAndRecipientOrRecipientAndSenderOrderBySentAtAsc(a, b, a, b)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private MessageDto toDto(Message m) {
        return new MessageDto(m.getId(), m.getSender().getUsername(), m.getRecipient().getUsername(),
                m.getContent(), m.getSentAt());
    }
}
