package com.chatapp.controller;

import com.chatapp.dto.MessageDto;
import com.chatapp.service.MessageService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{otherUsername}")
    public List<MessageDto> conversation(@PathVariable String otherUsername, Authentication authentication) {
        return messageService.conversation(authentication.getName(), otherUsername);
    }
}
