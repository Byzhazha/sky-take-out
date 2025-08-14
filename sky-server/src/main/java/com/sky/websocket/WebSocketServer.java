package com.sky.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketServer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 群发
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        simpMessagingTemplate.convertAndSend("/topic/public", message);
    }
}
