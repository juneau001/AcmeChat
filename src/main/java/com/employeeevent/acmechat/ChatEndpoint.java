/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.employeeevent.acmechat;

import jakarta.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 *
 * @author Juneau
 */
@ServerEndpoint(value = "/chatEndpoint/{username}",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class})
public class ChatEndpoint {
    
    @Inject
    ChatSessionController chatSessionController;

    private static Session session;
    private static Set<Session> chatters = new CopyOnWriteArraySet<>();

    @OnOpen
    public void messageOpen(Session session,
            @PathParam("username") String username) throws IOException,
            EncodeException {
        this.session = session;
        Map<String,String> chatusers = chatSessionController.getUsers();
        chatusers.put(session.getId(), username);
        chatSessionController.setUsers(chatusers);
        chatters.add(session);
        Message message = new Message();
        message.setUsername(username);
        message.setMessage("Welcome " + username);
        broadcast(message);
    }

    @OnMessage
    public void messageReceiver(Session session,
            Message message) throws IOException, EncodeException {
        Map<String,String> chatusers = chatSessionController.getUsers();
        message.setUsername(chatusers.get(session.getId()));
        broadcast(message);
    }

    @OnClose
    public void close(Session session) {
        chatters.remove(session);
        
        Map<String,String> chatusers = chatSessionController.getUsers();
        String chatuser = chatusers.get(session.getId());
        chatusers.remove(chatuser);
        Message message = new Message();
        message.setUsername(chatuser);
        message.setMessage("Disconnected from server");

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("There has been an error with session " + session.getId());
    }

    private static void broadcast(Message message)
            throws IOException, EncodeException {
       
        chatters.forEach(session -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
