package com.madbid.web.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * Created by dimer on 8/30/14.
 */
public class InitialWebsocketHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        if (request.getPrincipal() == null) {
            return new Principal() {
                @Override
                public String getName() {
                    return "guest";
                }
            };
        }
        return request.getPrincipal();
    }
}
