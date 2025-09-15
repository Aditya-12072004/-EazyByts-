package com.meeting_point.service;

import com.meeting_point.model.Message;
import com.meeting_point.Repository.MessageRepository; // This import is crucial
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Messages ko database mein save karne ke liye
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    // Do users ke beech ki chat history ko fetch karne ke liye
    public List<Message> getChatHistory(String chatRoomId) {
        return messageRepository.findByChatRoomIdOrderByTimestampAsc(chatRoomId);
    }
}