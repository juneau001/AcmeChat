/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.employeeevent.acmechat;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juneau
 */
@ClientEndpoint
public class BasicClient {
    
    Session session = null;
    private MessageHandler handler;
    
    public BasicClient(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        try {
        session.getBasicRemote().sendText("Opening connection");
        } catch (IOException ex){
            System.out.println(ex);
        }
    }
    
    public void addMessageHandler(MessageHandler msgHandler) {
        this.handler = msgHandler;
    }
    
    @OnMessage
    public void processMessage(String message) {
        System.out.println("Received message in client: " + message);
    }
    
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            Logger.getLogger(BasicClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
     public static interface MessageHandler {

        public void handleMessage(String message);
    }
}
