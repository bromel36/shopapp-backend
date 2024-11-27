package vn.ptithcm.shopapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptithcm.shopapp.model.response.ChatResponse;
import vn.ptithcm.shopapp.service.IChatService;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {


    private final IChatService chatService;

    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Object> processMessage(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        ChatResponse response = chatService.handleMessage(message);
        return ResponseEntity.ok(response);
    }
}

