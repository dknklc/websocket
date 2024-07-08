package com.dekankilic.websocket.controller;

import com.dekankilic.websocket.dto.ChatNotification;
import com.dekankilic.websocket.model.ChatMessage;
import com.dekankilic.websocket.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate; // This is the object that will allow us to send an object or message to a queue.

    // chat mapping or the message mapping which will create each time a new queue for user if it does not exist, otherwise it will just publish a message to the user queue

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);

        // we need something to publish our message or an object to the queue of our recipientId
        // john/queue/messages
        messagingTemplate.convertAndSendToUser(
                savedMsg.getRecipientId(),
                "/queue/messages",
                ChatNotification.builder()
                        .chatId(savedMsg.getChatId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                        .build()
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId, @PathVariable String recipientId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
