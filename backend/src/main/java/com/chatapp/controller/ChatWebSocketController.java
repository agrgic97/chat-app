package com.chatapp.controller;

import com.chatapp.dto.ChatMessageRequest;
import com.chatapp.dto.MessageDto;
import com.chatapp.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatWebSocketController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.send")
    public void send(ChatMessageRequest request, Principal principal) {
        String from = principal.getName();
        MessageDto saved = messageService.save(from, request.getTo(), request.getContent());

        messagingTemplate.convertAndSendToUser(request.getTo(), "/queue/messages", saved);
        messagingTemplate.convertAndSendToUser(from, "/queue/messages", saved);
    }
}
