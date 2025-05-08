package vn.ptithcm.shopapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptithcm.shopapp.model.response.ChatResponse;
import vn.ptithcm.shopapp.service.IChatService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
@Tag(name = "Chat")
public class ChatController {

    private final IChatService chatService;

    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    @Operation(summary = "Process chat message", description = "Process a chat message and return a response based on the message content.")
    public ResponseEntity<Object> processMessage(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        ChatResponse response = chatService.handleMessage(message);
        return ResponseEntity.ok(response);
    }
}
