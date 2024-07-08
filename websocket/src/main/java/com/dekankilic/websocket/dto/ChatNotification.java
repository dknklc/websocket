package com.dekankilic.websocket.dto;

import lombok.Builder;

@Builder
public record ChatNotification(
        String chatId,
        String senderId,
        String recipientId,
        String content
) {
}
