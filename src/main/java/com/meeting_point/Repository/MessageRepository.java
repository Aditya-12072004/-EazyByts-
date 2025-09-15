
package com.meeting_point.Repository;

import com.meeting_point.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Pichle method ke saath naya method add kiya gaya hai
    List<Message> findByChatRoomIdOrderByTimestampAsc(String chatRoomId);
}// package com.meeting_point.Repository;

// import com.meeting_point.model.Message;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.List;

// public interface MessageRepository extends JpaRepository<Message, Long> {
//     // Agar private messaging hai toh yeh method useful hogi
//     List<Message> findBySenderIdAndReceiverIdOrderByTimestampAsc(Long senderId, Long receiverId);
// }