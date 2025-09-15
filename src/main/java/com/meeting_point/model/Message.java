package com.meeting_point.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Is line ko badlen
    // private Long senderId;
    private String senderId; // senderId ko String kiya gaya hai
    private String receiverId; // receiverId ko bhi String kiya gaya hai
    private String content;

    private String chatRoomId;

    private LocalDateTime timestamp;
}
// package com.meeting_point.model;

// import jakarta.persistence.*;
// import lombok.Data;
// import java.time.LocalDateTime;

// @Entity
// @Data
// @Table(name = "messages")
// public class Message {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private Long senderId;
//     private Long receiverId;
//     private String content;

//     // Naya field add kiya gaya hai
//     private String chatRoomId;

//     private LocalDateTime timestamp;
// }