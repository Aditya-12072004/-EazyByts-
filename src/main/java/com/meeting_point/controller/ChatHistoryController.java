package com.meeting_point.controller;

import com.meeting_point.model.Message;
import com.meeting_point.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/history/{chatRoomId}")
    public List<Message> getChatHistory(@PathVariable String chatRoomId) {
        return messageService.getChatHistory(chatRoomId);
    }
}