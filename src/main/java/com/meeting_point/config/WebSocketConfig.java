package com.meeting_point.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // withSockJS() mein setAllowedOriginPatterns add kiya gaya hai
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://127.0.0.1:5500", "http://localhost:5500")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/user");
        registry.setUserDestinationPrefix("/user");
    }
}
// package com.meeting_point.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// @Configuration
// @EnableWebSocketMessageBroker
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         // WebSocket connection ke liye endpoint register karein.
//         // `ws://localhost:8080/ws` par connection banegi.
//         registry.addEndpoint("/ws").withSockJS();
//     }

//     @Override
//     public void configureMessageBroker(MessageBrokerRegistry registry) {
//         // Yahan message prefixes configure karte hain.
//         // /app se shuru hone wale messages @MessageMapping annotated methods ko route honge.
//         registry.setApplicationDestinationPrefixes("/app");
//         // /topic se shuru hone wale messages public broadcast ke liye hain.
//         // /user se shuru hone wale messages private users ke liye hain.
//         registry.enableSimpleBroker("/topic", "/user");
//         registry.setUserDestinationPrefix("/user");
//     }
// }