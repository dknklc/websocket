package com.dekankilic.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // We need to override 3 methods here.
    // The first one is about configuring our broker.

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user"); // destination of our broker will be /user
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/ser");
    }

    // Next, we need to register our STOMP end points

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // this is the path for our websocket.
                .withSockJS();
    }

    // Lastly, we need to implement how we want to convert our messages , so we need to add a message converter.
    // This will handle the conversion or like the serialization and deserialization of our messages.

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {

        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON); // we want to use application_json

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter(); // we need to provide what is the mapping or the converter that we want to use
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);

        messageConverters.add(converter); // we need to add the converter that we have to the parameter list of messageConverters
        return false;
    }
}
