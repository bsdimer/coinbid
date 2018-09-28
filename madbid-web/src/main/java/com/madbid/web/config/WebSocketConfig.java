package com.madbid.web.config;

import com.madbid.web.websocket.InitialWebsocketHandler;
import org.slf4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by dimer on 8/26/14.
 */
@Configuration
@ComponentScan("com.madbid.web")
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        LOGGER.info("########################### Registering STOMP endpoints ################################");
        InitialWebsocketHandler handler = new InitialWebsocketHandler();
        registry.addEndpoint("/stompEndpoint").setHandshakeHandler(handler).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        LOGGER.info("########################### Registering Message brokers ################################");
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/queue", "/topic").setRelayHost("localhost").setRelayPort(61613);
    }
}
