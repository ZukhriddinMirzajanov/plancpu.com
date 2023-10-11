package com.plancpu.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(final String message) {
        messagingTemplate.convertAndSend("/topic/" + message, "Default message from WS service");
    }
}
