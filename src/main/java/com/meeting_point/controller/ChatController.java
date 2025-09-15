package com.meeting_point.controller;

import com.meeting_point.model.Message;
import com.meeting_point.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private MessageService messageService;

    // Public chat messages ko handle karne ke liye
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message chatMessage, Principal principal) {
        chatMessage.setSenderId(principal.getName());
        messageService.saveMessage(chatMessage);
        return chatMessage;
    }

    // Naya: Private messages ko handle karne ke liye
    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(@Payload Message chatMessage) {
        // Message ko database mein save karein
        messageService.saveMessage(chatMessage);

        // Private topic par message bhejein (sirf receiver ko)
        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiverId().toString(), // Receiver ka ID
                "/private", // Private topic
                chatMessage
        );
    }
}
// package com.meeting_point.controller;

// import com.meeting_point.model.Message;
// import com.meeting_point.service.MessageService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.messaging.simp.SimpMessageSendingOperations;
// import org.springframework.stereotype.Controller;
// import java.security.Principal;

// @Controller
// public class ChatController {

//     @Autowired
//     private SimpMessageSendingOperations messagingTemplate;

//     @Autowired
//     private MessageService messageService;

//     // Public chat messages ko handle karne ke liye
//     @MessageMapping("/chat.sendMessage")
//     @SendTo("/topic/public")
//     public Message sendMessage(@Payload Message chatMessage, Principal principal) {
//         // Principal se username lekar senderId set karen
//         chatMessage.setSenderId(principal.getName());
//         messageService.saveMessage(chatMessage);
//         return chatMessage;
//     }

//     // Private messages ko handle karne ke liye
//     @MessageMapping("/chat.sendPrivateMessage")
//     public void sendPrivateMessage(@Payload Message chatMessage) {
//         messageService.saveMessage(chatMessage);

//         messagingTemplate.convertAndSendToUser(
//                 chatMessage.getReceiverId().toString(),
//                 "/private",
//                 chatMessage
//         );
//     }
// }